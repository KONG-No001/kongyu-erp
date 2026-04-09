package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.RoleAccountAssociation;
import link.kongyu.erp.modules.sys.mapper.RoleAccountAssociationMapper;
import link.kongyu.erp.modules.sys.service.RoleAccountAssociationBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RoleAccountAssociationBaseServiceImpl extends ServiceImpl<RoleAccountAssociationMapper, RoleAccountAssociation>
        implements RoleAccountAssociationBaseService {

    @Override
    @Transactional
    public RoleAccountAssociation bind(Long roleId, Long accountId, Long userId) {
        synchronized (buildLock(roleId, accountId)) {
            RoleAccountAssociation entity = baseMapper.selectIncludingDeleted(roleId, accountId);
            LocalDateTime now = LocalDateTime.now();

            if (entity == null) {
                entity = new RoleAccountAssociation();
                entity.setRoleId(roleId);
                entity.setAccountId(accountId);
                entity.setEnabled(Boolean.TRUE);
                entity.setDeleted(false);
                entity.setCreateInfo(userId, now);
                entity.setUpdateInfo(userId, now);
                save(entity);
                return entity;
            }

            if (Boolean.TRUE.equals(entity.getDeleted()) || !Boolean.TRUE.equals(entity.getEnabled())) {
                entity.setDeleted(false);
                entity.setEnabled(Boolean.TRUE);
                entity.setUpdateInfo(userId, now);
                updateById(entity);
            }
            return entity;
        }
    }

    @Override
    @Transactional
    public void unbind(Long roleId, Long accountId, Long userId) {
        synchronized (buildLock(roleId, accountId)) {
            RoleAccountAssociation entity = lambdaQuery()
                    .eq(RoleAccountAssociation::getRoleId, roleId)
                    .eq(RoleAccountAssociation::getAccountId, accountId)
                    .one();
            if (entity == null) {
                return;
            }
            entity.setDeleted(true);
            entity.setEnabled(Boolean.FALSE);
            entity.setUpdateInfo(userId, LocalDateTime.now());
            updateById(entity);
        }
    }

    private String buildLock(Long roleId, Long accountId) {
        return ("role-account:" + roleId + ':' + accountId).intern();
    }
}
