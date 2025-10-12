package link.kongyu.erp.model.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.core.exception.ServiceException;
import link.kongyu.erp.model.sys.mapper.AccountIMapper;
import link.kongyu.erp.model.sys.po.Account;
import link.kongyu.erp.model.sys.service.AccountIService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountIMapper, Account> implements AccountIService {

    @Override
    public Account getInfoById(String id) {
        Account account = getById(id);

        if (account == null) {
            throw new ServiceException("Account is not found!!!");
        }

        return account;
    }
}
