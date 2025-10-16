package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */

@Service
public class AccountQueryPageServiceImpl implements AccountQueryPageIService {

    @Autowired
    AccountBaseService accountBaseService;

    @Override
    public PageResult<Account> findAccountPage(PageRequest pageRequest) {

        LambdaQueryChainWrapper<Account> wrapper = accountBaseService.lambdaQuery();

        pageRequest.whenField("name", pageSearch -> {
            String value = (String) pageSearch.getValue();
            if (value != null && !value.isEmpty()) {
                if (value.contains(",") || value.contains(" ")) {
                    wrapper.in(Account::getUsername, Arrays.asList(value.split(",")));
                }
                else {
                    wrapper.like(Account::getUsername, value);
                }
            }
        });

        return PageUtils.toPageResult(accountBaseService.page(PageUtils.toMpPage(pageRequest), wrapper.getWrapper()));
    }
}
