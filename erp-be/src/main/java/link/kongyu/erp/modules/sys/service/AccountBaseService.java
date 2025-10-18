package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;

public interface AccountBaseService extends IService<Account> {

    AccountSimpleInfoDto getSimpleInfoById(String id);

    void addAccount(Account account, long userId);

    void updateAccount(Account account, long userId);

    void enableAccount(long id, boolean enable, long userId);
}
