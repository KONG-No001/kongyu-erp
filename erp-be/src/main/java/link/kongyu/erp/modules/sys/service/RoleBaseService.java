package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Role;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */
public interface RoleBaseService extends IService<Role> {
    Role create(Role role, Long userId);

    Role update(Long id, Role role, Long userId);

    void delete(Long id, Long userId);

    Role detail(Long id);

    PageResult<Role> search(PageRequest pageRequest, QueryWrapper<Role> queryWrapper);

    void addRole(Role role, long userId);

    void updateRole(Role role, long userId);

    void enableRole(long id, boolean enable, long userId);

    BatchingResult batchEnableRole(long[] ids, boolean enable, long userId);
}
