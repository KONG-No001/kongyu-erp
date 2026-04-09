package link.kongyu.erp.modules.sys.service;

import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.service.QueryPageService;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Role;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */
public interface RoleQueryPageService extends QueryPageService {

    PageResult<Role> findRolePage(PageRequest pageRequest);


}
