package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.modules.sys.entity.Account;

public interface AccountBaseService extends IService<Account> {

    Account create(Account account, Long userId);

    Account update(Long id, Account account, Long userId);

    void enable(long id, boolean enable, long userId);

    BatchingResult batchEnable(long[] ids, boolean enable, long userId);

    Account detail(Long id);

}
