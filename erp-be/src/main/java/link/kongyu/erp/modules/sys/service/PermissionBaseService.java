package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Permission;

public interface PermissionBaseService extends IService<Permission> {

    Permission create(Permission permission, Long userId);

    Permission update(Long id, Permission permission, Long userId);

    void delete(Long id, Long userId);

    Permission detail(Long id);

    PageResult<Permission> search(PageRequest pageRequest, QueryWrapper<Permission> queryWrapper);
}
