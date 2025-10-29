package link.kongyu.erp.modules.sys.service;

import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.service.QueryPageService;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */
public interface AccountQueryPageService extends QueryPageService {
    PageResult<Account> findAccountPage(PageRequest pageRequest);

    PageResult<AccountSimpleInfoDto> findAccountSimpleInfoPage(PageRequest pageRequest);
}
