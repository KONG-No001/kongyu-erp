package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.batching.metadata.BatchingEntity;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.modules.sys.entity.Permission;
import link.kongyu.erp.modules.sys.mapper.PermissionMapper;
import link.kongyu.erp.modules.sys.service.PermissionBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static link.kongyu.erp.modules.sys.constants.PermissionFields.*;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Service
@Slf4j
public class PermissionBaseServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionBaseService {

    @Override
    public Permission getInfoById(long id) {
        Permission entity = getById(id);
        if (entity == null) {
            log.warn("没有找到 Id[{}] 的 Permission 的记录", id);
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "未找到记录");
        }
        return entity;
    }

    @Override
    @Transactional
    public void addPermission(Permission permission, long userId) {
        checkForDuplicateByGroupAndName(permission);
        checkForDuplicateByResource(permission);

        LocalDateTime now = LocalDateTime.now();
        permission.setCreateInfo(userId, now);
        permission.setUpdateInfo(userId, now);
        permission.setEnabled(true);
        permission.setDeleted(false);

        save(permission);
    }

    @Override
    @Transactional
    public void updatePermission(Permission permission, long userId) {

        checkForDuplicateByGroupAndName(permission);
        checkForDuplicateByResource(permission);

        Permission entity = getInfoById(permission.getId());

        MyBeanUtils.mergeProperties(permission, entity, FIELD_ID, FIELD_CREATED_BY, FIELD_CREATED_DATE, FIELD_DELETE);
        entity.setUpdateInfo(userId, LocalDateTime.now());

        updateById(entity);
    }

    @Override
    @Transactional
    public BatchingResult batchDeletePermission(long[] ids, long userId) {
        BatchingEntity batchingEntity = new BatchingEntity(ids.length);
        for (long id : ids) {
            try {
                removeById(id);
            }
            catch (Exception e) {
                batchingEntity.addErrorMessage(String.format("ID[%s] 操作失败。原因: %s", id, e.getMessage()));
            }
        }

        log.info("批量删除{}", batchingEntity.getSummary());

        return batchingEntity.toResult();
    }

    @Override
    @Transactional
    public BatchingResult batchEnablePermission(long[] ids, boolean enable, long userId) {
        BatchingEntity batchingEntity = new BatchingEntity(ids.length);
        for (long id : ids) {
            try {
                enablePermission(id, enable, userId);
            }
            catch (Exception e) {
                batchingEntity.addErrorMessage(String.format("ID[%s] 操作失败。原因: %s", id, e.getMessage()));
            }
        }

        log.info("批量删除{}", batchingEntity.getSummary());

        return batchingEntity.toResult();
    }

    @Override
    @Transactional
    public void enablePermission(long id, boolean enable, long userId) {
        if (count(lambdaQuery().eq(FIELD_MAPPINGS.get(FIELD_ID), id).getWrapper()) == 0) {
            log.warn("没有找到 Id[{}] 的 Permission 的记录", id);
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "未找到记录");
        }

        update(lambdaUpdate().eq(FIELD_MAPPINGS.get(FIELD_ID), id).set(FIELD_MAPPINGS.get(FIELD_ENABLED), enable).getWrapper());
    }

    private void checkForDuplicateByGroupAndName(Permission permission) {
        // 检查资名称与业务是否重复
        Long id = permission.getId();
        String businessGroup = permission.getBusinessGroup();
        String permissionName = permission.getPermissionName();
        LambdaQueryChainWrapper<Permission> wrapper = lambdaQuery()
                .eq(FIELD_MAPPINGS.get(FIELD_BUSINESS_GROUP), businessGroup)
                .eq(FIELD_MAPPINGS.get(FIELD_PERMISSION_NAME), permissionName)
                .not(id != null, w -> w.eq(FIELD_MAPPINGS.get(FIELD_ID), id));

        if (count(wrapper.getWrapper()) > 1) {
            log.warn("权限名称 {} 已存在于 {} 业务组中", permissionName, businessGroup);
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "权限名称已存在于业务组中");
        }
    }

    private void checkForDuplicateByResource(Permission permission) {
        // 检查资源字符串是否重复
        Long id = permission.getId();
        String accessResource = permission.getAccessResource();
        LambdaQueryChainWrapper<Permission> wrapper = lambdaQuery()
                .eq(FIELD_MAPPINGS.get(FIELD_ACCESS_RESOURCE), accessResource)
                .not(id != null, w -> w.eq(FIELD_MAPPINGS.get(FIELD_ID), id));

        if (count(wrapper.getWrapper()) > 1) {
            log.warn("资源名称 {} 重复", accessResource);
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "已经存在相同的资源名称");
        }
    }
}
