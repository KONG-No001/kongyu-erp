package link.kongyu.erp.model.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.model.sys.po.Account;

public interface AccountIService extends IService<Account> {

    Account getInfoById(String id);

}
