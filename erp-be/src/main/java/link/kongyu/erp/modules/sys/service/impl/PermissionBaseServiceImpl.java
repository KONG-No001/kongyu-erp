package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Permission;
import link.kongyu.erp.modules.sys.mapper.PermissionMapper;
import link.kongyu.erp.modules.sys.service.PermissionBaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PermissionBaseServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionBaseService {

    @Override
    @Transactional
    public Permission create(Permission permission, Long userId) {
        String permissionName = permission.getPermissionName();
        if (count(lambdaQuery().eq(Permission::getPermissionName, permissionName).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "权限名'" + permissionName + "'重复");
        }

        Permission entity = new Permission();
        BeanUtils.copyProperties(permission, entity, "id", "createdBy", "createdDate", "updatedBy", "updatedDate", "deleted");

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateInfo(userId, now);
        entity.setUpdateInfo(userId, now);
        entity.setDeleted(false);
        if (entity.getEnabled() == null) {
            entity.setEnabled(Boolean.TRUE);
        }

        save(entity);
        return entity;
    }

    @Override
    @Transactional
    public Permission update(Long id, Permission permission, Long userId) {
        Permission entity = detail(id);
        String permissionName = permission.getPermissionName();
        if (permissionName != null && count(lambdaQuery()
                .eq(Permission::getPermissionName, permissionName)
                .ne(Permission::getId, id)
                .getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "权限名'" + permissionName + "'重复");
        }

        MyBeanUtils.mergeProperties(permission, entity, "id", "createdBy", "createdDate", "updatedBy", "updatedDate", "deleted");
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
        return entity;
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        Permission entity = detail(id);
        entity.setDeleted(true);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    public Permission detail(Long id) {
        Permission entity = getById(id);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "权限不存在");
        }
        return entity;
    }

    @Override
    public PageResult<Permission> search(PageRequest pageRequest, QueryWrapper<Permission> queryWrapper) {
        pageRequest.validate();
        Page<Permission> page = page(PageUtils.toMpPage(pageRequest), queryWrapper);
        return PageResult.getInstance(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }
}
