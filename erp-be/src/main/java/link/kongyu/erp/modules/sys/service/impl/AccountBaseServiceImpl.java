package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.batching.metadata.BatchProcessingResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.mapper.AccountMapper;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static link.kongyu.erp.modules.sys.constants.AccountFields.*;

@Service
public class AccountBaseServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountBaseService {

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
    public void addAccount(AccountSimpleInfoDto account, long userId) {

        String username = account.getUsername();
        if (count(lambdaQuery().eq(FIELD_MAPPINGS.get(FIELD_USERNAME), username).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(account, entity);

        LocalDateTime now = LocalDateTime.now();

        entity.setCreateInfo(userId, now);
        entity.setUpdateInfo(userId, now);
        entity.setEnabled(true);
        entity.setDeleted(false);

        // TODO 密码管理
        entity.setPassword("{noop}123456");

        save(entity);
    }

    @Override
    @Transactional
    public void updateAccount(AccountSimpleInfoDto account, long userId) {

        long accountId = account.getId();
        String username = account.getUsername();

        if (count(lambdaQuery().eq(FIELD_MAPPINGS.get(FIELD_USERNAME), username).not(wrapper -> wrapper.eq(FIELD_MAPPINGS.get(FIELD_ID), accountId)).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = getById(accountId);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户不存在");
        }

        MyBeanUtils.mergeProperties(account, entity, FIELD_ID, FIELD_ENABLED);
        entity.setUpdateInfo(userId, LocalDateTime.now());

        updateById(entity);
    }

    @Override
    @Transactional
    public void enableAccount(boolean enable, long userId, long id) {
        Account entity = getById(id);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户不存在");
        }
        entity.setEnabled(enable);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    @Transactional
    public BatchProcessingResult batchEnableAccount(long[] ids, boolean enable, long userId) {
        BatchProcessingResult batchProcessingResult = new BatchProcessingResult(ids.length);
        for (long id : ids) {
            try {
                enableAccount(enable, userId, id);
                batchProcessingResult.incrementSuccessCount();
            }
            catch (Exception e) {
                batchProcessingResult.incrementErrorCount();
            }
        }
        return batchProcessingResult;
    }


}
