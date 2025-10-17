package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */

@Service
public class AccountQueryPageServiceImpl implements AccountQueryPageIService {

    /* 查询字段通用名 */
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_ACCOUNT_TYPE = "accountType";


    @Autowired
    AccountBaseService accountBaseService;

    @Override
    public PageResult<Account> findAccountPage(PageRequest pageRequest) {
        pageRequest.validate();

        LambdaQueryChainWrapper<Account> wrapper = accountBaseService.lambdaQuery();

        buildQueryConditions(wrapper, pageRequest);

        buildSortConditions(wrapper, pageRequest);

        return PageUtils.toPageResult(accountBaseService.page(PageUtils.toMpPage(pageRequest), wrapper.getWrapper()));
    }

    private void buildQueryConditions(LambdaQueryChainWrapper<Account> wrapper, PageRequest pageRequest) {
        pageRequest.whenField(FIELD_USERNAME, pageSearch -> {
            String value = ObjectUtils.nullSafeToString(pageSearch.getValue());
            if (!StringUtils.isEmpty(value)) {
                if (value.contains(",") || value.contains(" ")) {
                    // 多值查询
                    List<String> usernames = Arrays
                            .stream(value.replace(" ", ",").split(","))
                            .map(String::trim)
                            .filter(str -> !StringUtils.isEmpty(str))
                            .collect(Collectors.toList());
                    if (!usernames.isEmpty()) {
                        wrapper.in(Account::getUsername, usernames);
                    }
                }
                else {
                    // 模糊查询
                    wrapper.like(Account::getUsername, value.trim());
                }
            }
        });

        pageRequest.whenFieldList(FIELD_ACCOUNT_TYPE, pageSearches -> {
            if (!pageSearches.isEmpty()) {
                List<String> types = pageSearches
                        .stream()
                        .map(t -> ObjectUtils.nullSafeToString(t.getValue()).trim())
                        .filter(str -> !StringUtils.isEmpty(str))
                        .collect(Collectors.toList());
                if (!types.isEmpty()) {
                    wrapper.in(Account::getType, types);
                }
            }
        });
    }

    private void buildSortConditions(LambdaQueryChainWrapper<Account> wrapper, PageRequest pageRequest) {
        pageRequest.getSorts().forEach(pageSort -> {
            String field = pageSort.getField();
            SFunction<Account, ?> getFun = null;
            switch (field) {
                case FIELD_USERNAME:
                    getFun = Account::getUsername;
                    break;
                case FIELD_ACCOUNT_TYPE:
                    getFun = Account::getType;
                    break;
            }

            if (getFun != null) {
                if (Direction.DESC.equals(pageSort.getDirection())) {
                    wrapper.orderByDesc(getFun);
                }
                else {
                    wrapper.orderByAsc(getFun);
                }
            }
        });
    }

}
