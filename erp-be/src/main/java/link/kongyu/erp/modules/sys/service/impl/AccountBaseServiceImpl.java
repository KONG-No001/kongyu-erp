package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.logger.loggerFactory.MyLoggerFactory;
import link.kongyu.erp.common.logger.progress.IProgressLogger;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.batching.metadata.BatchingEntity;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.mapper.AccountMapper;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AccountBaseServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountBaseService {

    @Override
    @Transactional
    public Account create(Account account, Long userId) {
        addAccount(account, userId);
        return lambdaQuery().eq(Account::getUsername, account.getUsername()).one();
    }

    @Override
    @Transactional
    public Account update(Long id, Account account, Long userId) {
        account.setId(id);
        updateAccount(account, userId);
        return detail(id);
    }

    @Override
    @Transactional
    public void enable(long id, boolean enable, long userId) {
        Account entity = detail(id);
        entity.setEnabled(enable);
        entity.setUpdatedBy(userId);
        entity.setUpdatedTime(LocalDateTime.now());
        updateById(entity);
    }

    @Override
    @Transactional
    public BatchingResult batchEnable(long[] ids, boolean enable, long userId) {
        BatchingEntity batchingEntity = new BatchingEntity(ids.length);
        IProgressLogger progressLogger = MyLoggerFactory.getProgressLogger(log, ids.length, 0);
        int i = 0;
        for (long id : ids) {
            progressLogger.increment();
            try {
                enable(id, enable, userId);
                batchingEntity.incrementSuccessCount();
            }
            catch (Exception e) {
                progressLogger.warn(String.format("ID[%s]操作失败。原因: %s", id, e.getMessage()));
                batchingEntity.addErrorMessage(String.format("index([%s])操作失败。原因: %s", i, e.getMessage()));
            }
            i++;
        }

        log.info("批量禁用{}", batchingEntity.getSummary());

        return batchingEntity.toResult();
    }

    @Override
    public Account detail(Long id) {
        Account entity = getById(id);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户不存在");
        }
        return entity;
    }

    private void addAccount(Account account, long userId) {

        String username = account.getUsername();
        if (count(lambdaQuery().eq(Account::getUsername, username).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(account, entity, "id");

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedBy(userId);
        entity.setCreatedTime(now);
        entity.setUpdatedBy(userId);
        entity.setUpdatedTime(now);

        entity.setEnabled(true);

        // TODO 密码管理
        entity.setPassword("{noop}123456");

        save(entity);
    }

    private void updateAccount(Account account, long userId) {
        long accountId = account.getId();
        String username = account.getUsername();

        if (count(lambdaQuery().eq(Account::getUsername, username).not(wrapper -> wrapper.eq(Account::getId, accountId)).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = detail(accountId);

        MyBeanUtils.mergeProperties(account, entity, "id", "password");
        entity.setUpdatedBy(userId);
        entity.setUpdatedTime(LocalDateTime.now());

        updateById(entity);
    }

}
