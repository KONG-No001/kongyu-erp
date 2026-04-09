package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoView;

public interface AccountBaseService extends IService<Account> {

    Account create(Account account, Long userId);

    Account update(Long id, Account account, Long userId);

    void delete(Long id, Long userId);

    Account detail(Long id);

    PageResult<Account> search(PageRequest pageRequest, QueryWrapper<Account> queryWrapper);

    AccountSimpleInfoView getSimpleInfoById(String id);

    void addAccount(Account account, long userId);

    void updateAccount(Account account, long userId);

    void enableAccount(long id, boolean enable, long userId);

    BatchingResult batchEnableAccount(long[] ids, boolean enable, long userId);
}
