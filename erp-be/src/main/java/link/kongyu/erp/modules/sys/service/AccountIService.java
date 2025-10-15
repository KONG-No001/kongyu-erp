package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;

public interface AccountIService extends IService<Account> {

    AccountSimpleInfoDto getSimpleInfoById(String id);
}
