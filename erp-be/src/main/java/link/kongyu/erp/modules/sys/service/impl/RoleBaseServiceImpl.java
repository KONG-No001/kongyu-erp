package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.logger.loggerFactory.MyLoggerFactory;
import link.kongyu.erp.common.logger.progress.IProgressLogger;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.batching.metadata.BatchingEntity;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Role;
import link.kongyu.erp.modules.sys.mapper.RoleMapper;
import link.kongyu.erp.modules.sys.service.RoleBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static link.kongyu.erp.modules.sys.constants.fields.RoleFields.*;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Service
@Slf4j
public class RoleBaseServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleBaseService {

    @Autowired
    RoleBaseServiceImpl baseService;

    @Override
    @Transactional
    public Role create(Role role, Long userId) {
        addRole(role, userId);
        return lambdaQuery().eq(Role::getRoleName, role.getRoleName()).one();
    }

    @Override
    @Transactional
    public Role update(Long id, Role role, Long userId) {
        role.setId(id);
        updateRole(role, userId);
        return detail(id);
    }

    @Override
    @Transactional
    public void delete(Long id, Long userId) {
        Role entity = detail(id);
        entity.setDeleted(true);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    public Role detail(Long id) {
        Role entity = getById(id);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "角色不存在");
        }
        return entity;
    }

    @Override
    public PageResult<Role> search(PageRequest pageRequest, QueryWrapper<Role> queryWrapper) {
        pageRequest.validate();
        Page<Role> page = page(PageUtils.toMpPage(pageRequest), queryWrapper);
        return PageResult.getInstance(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    @Override
    @Transactional
    public void addRole(Role role, long userId) {
        String roleName = role.getRoleName();

        if (count(lambdaQuery().eq(SF_ROLE_NAME, roleName).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "角色名'" + roleName + "'重复");
        }

        Role entity = new Role();
        BeanUtils.copyProperties(role, entity, F_ID);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateInfo(userId, now);
        entity.setUpdateInfo(userId, now);
        entity.setEnabled(true);
        entity.setDeleted(false);

        save(entity);
    }

    @Override
    @Transactional
    public void updateRole(Role role, long userId) {
        long id = role.getId();
        String roleName = role.getRoleName();

        if (count(lambdaQuery().eq(SF_ROLE_NAME, roleName).not(w -> w.eq(SF_ID, id)).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "角色名'" + roleName + "'重复");
        }

        Role entity = detail(id);

        MyBeanUtils.mergeProperties(role, entity, F_ID, F_CREATED_BY, F_CREATED_DATE, F_DELETE);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    @Transactional
    public void enableRole(long id, boolean enable, long userId) {
        Role entity = detail(id);
        entity.setEnabled(enable);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    public BatchingResult batchEnableRole(long[] ids, boolean enable, long userId) {
        BatchingEntity batchingEntity = new BatchingEntity(ids.length);

        IProgressLogger progressLogger = MyLoggerFactory.getProgressLogger(log, ids.length, 0);
        int i = 0;
        for (long id : ids) {
            progressLogger.increment("处理进度更新");
            try {
                baseService.enableRole(id, enable, userId);
                batchingEntity.incrementSuccessCount();
            }
            catch (Exception e) {
                progressLogger.warn(String.format("ID[%s]操作失败。原因: %s", id, e.getMessage()));
                batchingEntity.addErrorMessage(String.format("[%s]操作失败。原因: %s", i, e.getMessage()));
            }
            i++;
        }

        return batchingEntity.toResult();
    }
}
