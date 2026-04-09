package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.PermissionRoleAssociation;
import link.kongyu.erp.modules.sys.mapper.PermissionRoleAssociationMapper;
import link.kongyu.erp.modules.sys.service.PermissionRoleAssociationBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PermissionRoleAssociationBaseServiceImpl extends ServiceImpl<PermissionRoleAssociationMapper, PermissionRoleAssociation>
        implements PermissionRoleAssociationBaseService {

    @Override
    @Transactional
    public PermissionRoleAssociation bind(Long permissionId, Long roleId, Long userId) {
        synchronized (buildLock(permissionId, roleId)) {
            PermissionRoleAssociation entity = baseMapper.selectIncludingDeleted(roleId, permissionId);
            LocalDateTime now = LocalDateTime.now();

            if (entity == null) {
                entity = new PermissionRoleAssociation();
                entity.setPermissionId(permissionId);
                entity.setRoleId(roleId);
                entity.setDeleted(false);
                entity.setCreateInfo(userId, now);
                entity.setUpdateInfo(userId, now);
                save(entity);
                return entity;
            }

            if (Boolean.TRUE.equals(entity.getDeleted())) {
                entity.setDeleted(false);
                entity.setUpdateInfo(userId, now);
                updateById(entity);
            }
            return entity;
        }
    }

    @Override
    @Transactional
    public void unbind(Long permissionId, Long roleId, Long userId) {
        synchronized (buildLock(permissionId, roleId)) {
            PermissionRoleAssociation entity = lambdaQuery()
                    .eq(PermissionRoleAssociation::getPermissionId, permissionId)
                    .eq(PermissionRoleAssociation::getRoleId, roleId)
                    .one();
            if (entity == null) {
                return;
            }
            entity.setDeleted(true);
            entity.setUpdateInfo(userId, LocalDateTime.now());
            updateById(entity);
        }
    }

    private String buildLock(Long permissionId, Long roleId) {
        return ("permission-role:" + permissionId + ':' + roleId).intern();
    }
}
