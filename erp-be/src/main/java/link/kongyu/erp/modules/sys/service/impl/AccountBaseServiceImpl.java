package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;
import link.kongyu.erp.common.logger.loggerFactory.MyLoggerFactory;
import link.kongyu.erp.common.logger.progress.IProgressLogger;
import link.kongyu.erp.common.utils.MyBeanUtils;
import link.kongyu.erp.core.batching.metadata.BatchingEntity;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.mapper.AccountMapper;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static link.kongyu.erp.modules.sys.constants.fields.AccountFields.F_ID;
import static link.kongyu.erp.modules.sys.constants.fields.AccountFields.F_PASSWORD;

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
    public void delete(Long id, Long userId) {
        Account entity = detail(id);
        entity.setDeleted(true);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    public Account detail(Long id) {
        Account entity = getById(id);
        if (entity == null) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户不存在");
        }
        return entity;
    }

    @Override
    public PageResult<Account> search(PageRequest pageRequest, QueryWrapper<Account> queryWrapper) {
        pageRequest.validate();
        Page<Account> page = page(PageUtils.toMpPage(pageRequest), queryWrapper);
        return PageResult.getInstance(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    @Override
    public AccountSimpleInfoView getSimpleInfoById(String id) {
        Account account = getById(id);
        if (account == null) {
            throw new ServiceException("账户不存在");
        }
        AccountSimpleInfoView simpleInfo = new AccountSimpleInfoView();
        BeanUtils.copyProperties(account, simpleInfo);

        return simpleInfo;
    }

    @Override
    @Transactional
    public void addAccount(Account account, long userId) {

        String username = account.getUsername();
        if (count(lambdaQuery().eq(Account::getUsername, username).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = new Account();
        BeanUtils.copyProperties(account, entity, F_ID);

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
    public void updateAccount(Account account, long userId) {
        long accountId = account.getId();
        String username = account.getUsername();

        if (count(lambdaQuery().eq(Account::getUsername, username).not(wrapper -> wrapper.eq(Account::getId, accountId)).getWrapper()) > 0) {
            throw new ServiceException(ResponseCode.PARAM_VALID_ERROR, "用户名已存在");
        }

        Account entity = detail(accountId);

        MyBeanUtils.mergeProperties(account, entity, F_ID, F_PASSWORD);
        entity.setUpdateInfo(userId, LocalDateTime.now());

        updateById(entity);
    }

    @Override
    @Transactional
    public void enableAccount(long id, boolean enable, long userId) {
        Account entity = detail(id);
        entity.setEnabled(enable);
        entity.setUpdateInfo(userId, LocalDateTime.now());
        updateById(entity);
    }

    @Override
    @Transactional
    public BatchingResult batchEnableAccount(long[] ids, boolean enable, long userId) {
        BatchingEntity batchingEntity = new BatchingEntity(ids.length);
        IProgressLogger progressLogger = MyLoggerFactory.getProgressLogger(log, ids.length, 0);
        int i = 0;
        for (long id : ids) {
            progressLogger.increment();
            try {
                enableAccount(id, enable, userId);
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


}
