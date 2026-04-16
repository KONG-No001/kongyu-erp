# 02-账号与权限（RBAC）详细设计

基于《01-账号与权限（RBAC）系统设计.md》的顶层约束，本详细设计旨在指导核心鉴权机制的落地，明确类结构、组件交互与关键事务边界。

## 1. Spring Security 接入与核心类规划
安全控制逻辑应与业务逻辑解耦，统一收敛于 `link.kongyu.erp.infrastructure.security` 包下。

### 1.1 核心组件清单
- **`config.SecurityConfig`**：安全核心配置，负责组装 Filter 链、放行白名单（如登录接口）、配置哈希算法。
- **`userdetails.RbacUserDetails`**：实现 Spring 的 `UserDetails` 接口，内部包装 `Account` 实体及解析后的权限列表。
- **`userdetails.RbacUserDetailsService`**：实现 `UserDetailsService` 接口，桥接数据库与 Security 框架。
- **`auth.RestAuthenticationEntryPoint`**：未登录（401）异常处理器，返回统一的 `Result.failure()` 格式。
- **`auth.RestAccessDeniedHandler`**：无权限（403）异常处理器，返回统一的 `Result.failure()` 格式。

### 1.2 权限标识 (Authority) 映射规则
为了避免实体污染，在 `RbacUserDetailsService` 加载数据时，需将 `Role` 和 `Permission` 转换为 Spring Security 识别的字符串标识：
- **角色映射**：增加 `ROLE_` 前缀，如 `ROLE_ADMIN`。
- **接口权限映射**：对于 `type = API` 的资源，建议转换为 `PERM:API:{method}:{path}` 格式，例如 `PERM:API:POST:/account/search`。

### 1.3 数据库查询收敛
**禁止**在基础的 `AccountMapper` 中手写复杂的连表鉴权查询。建议新建 `AccountSecurityMapper`：
1. 校验账号状态（`enabled=1` 且 `deleted=0`）。
2. `JOIN` 关联表查出有效的角色与权限。

## 2. 会话 (Session) 与外部 API 整合方案

### 2.1 状态存储机制
- **内部流转**：依赖 Servlet 容器标准的 `HttpSession` 机制，登录成功后在服务侧生成并保存 Session 树，响应时通过 `Set-Cookie: JSESSIONID` 或自定义 Header（如 `x-auth-token`）返回凭证。
- **外部 API 整合**：第三方系统通过调用统一的 `/auth/login` 接口，拿到 Token（SessionId）后，在后续所有的 API 请求 Header 中携带该凭证，无缝融入现有的 `SecurityContextPersistenceFilter` 恢复链路。
- **扩展性兜底**：因现阶段为单体应用，暂用内存 Session；一旦演进为多实例，只需引入 `Spring Session Data Redis` 依赖即可实现零代码侵入的分布式 Session 改造。

## 3. 级联删除的 Service 层原子性保障

主实体（Role / Permission）的逻辑删除操作，必须与其下属所有中间关联表的逻辑删除保持在**绝对的同一事务**内。

### 3.1 落地代码规约（以删除 Role 为例）
必须在 `RoleBaseServiceImpl` 中显式声明 Spring 的声明式事务，并通过 MyBatis-Plus 的批量更新能力（避免在循环中发 SQL）清理关联表。

```java
@Service
public class RoleBaseServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleBaseService {

    @Autowired RoleAccountAssociationBaseService roleAccountService;
    @Autowired PermissionRoleAssociationBaseService permissionRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId, Long userId) {
        // 1. 删除主表（逻辑删除）
        Role role = getById(roleId);
        if (role == null) return;
        role.setDeleted(true);
        role.setUpdateInfo(userId, LocalDateTime.now());
        updateById(role);

        // 2. 级联清理：角色-账户关联
        roleAccountService.lambdaUpdate()
            .eq(RoleAccountAssociation::getRoleId, roleId)
            .set(RoleAccountAssociation::getDeleted, true)
            .set(RoleAccountAssociation::getEnabled, false) // 冗余置反
            .set(RoleAccountAssociation::getUpdatedBy, userId)
            .set(RoleAccountAssociation::getUpdatedDate, LocalDateTime.now())
            .update();

        // 3. 级联清理：角色-权限关联
        permissionRoleService.lambdaUpdate()
            .eq(PermissionRoleAssociation::getRoleId, roleId)
            .set(PermissionRoleAssociation::getDeleted, true)
            .set(PermissionRoleAssociation::getUpdatedBy, userId)
            .set(PermissionRoleAssociation::getUpdatedDate, LocalDateTime.now())
            .update();
    }
}
```

### 3.2 账户的“伪删除”规约
在 `AccountBaseServiceImpl` 中，删除接口必须被重写为“禁用”逻辑：
禁止调用 `baseMapper.deleteById()`，而是执行 `lambdaUpdate().set(Account::getEnabled, false)`，并且无需级联删除其关联表（保留其历史绑定记忆）。