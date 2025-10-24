package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.batching.metadata.BatchProcessingResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;

public interface AccountBaseService extends IService<Account> {

    AccountSimpleInfoDto getSimpleInfoById(String id);

    void addAccount(AccountSimpleInfoDto account, long userId);

    void updateAccount(AccountSimpleInfoDto account, long userId);

    void enableAccount(boolean enable, long userId, long id);

    BatchProcessingResult batchEnableAccount(long[] ids, boolean enable, long userId);
}
