package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.mapper.AccountIMapper;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountBaseServiceImpl extends ServiceImpl<AccountIMapper, Account> implements AccountBaseService {

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

    @Override
    @Transactional
    public void addAccount(Account account, long userId) {

        String username = account.getUsername();
        if (count(lambdaQuery().eq(Account::getUsername, username)) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(account, entity);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreateInfo(userId, now);
        entity.setUpdateInfo(userId, now);
        entity.setEnabled(true);

        save(entity);
    }

    @Override
    @Transactional
    public void updateAccount(Account account, long userId) {

        long accountId = account.getId();
        String username = account.getUsername();
        if (count(lambdaQuery().eq(Account::getUsername, username).not(wrapper -> wrapper.eq(Account::getId, accountId))) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        getById(accountId);

    }

    @Override
    public void enableAccount(long id, boolean enable, long userId) {

    }


    private void assertAccount(Account account) {

    }
}
