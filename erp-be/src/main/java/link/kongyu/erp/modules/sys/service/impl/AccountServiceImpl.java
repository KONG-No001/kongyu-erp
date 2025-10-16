package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.mapper.AccountIMapper;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountIMapper, Account> implements AccountBaseService {

    @Override
    public AccountSimpleInfoDto getSimpleInfoById(String id) {
        Account account = getById(id);
        if (account == null) {
            throw new ServiceException("账户不存在");
        }
        AccountSimpleInfoDto simpleInfo = new AccountSimpleInfoDto();
        BeanUtils.copyProperties(account, simpleInfo);

        return simpleInfo;
    }
}
