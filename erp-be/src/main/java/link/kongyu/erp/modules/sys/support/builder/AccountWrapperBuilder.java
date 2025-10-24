package link.kongyu.erp.modules.sys.support.builder;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.support.builder.BuilderUtils;
import link.kongyu.erp.core.page.support.builder.impl.GenericMpLambdaWrapperBuilder;
import link.kongyu.erp.modules.sys.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static link.kongyu.erp.modules.sys.constants.AccountFields.*;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/22
 */
@Component
public class AccountWrapperBuilder extends GenericMpLambdaWrapperBuilder<Account> {

    @Autowired
    public AccountWrapperBuilder() {
        super(FIELD_MAPPINGS, new HashSet<>(Arrays.asList(
                FIELD_ID,
                FIELD_CREATED_DATE,
                FIELD_CREATED_BY,
                FIELD_USERNAME,
                FIELD_TYPE,
                FIELD_ENABLED
        )));
    }

    @Override
    protected void buildConditions(LambdaQueryChainWrapper<Account> query, PageRequest request) {
        Set<PageSearch> searches = request.getSearches();
        if (searches.isEmpty()) {
            return;
        }

        for (PageSearch search : searches) {
            String field = search.getField();
            switch (field) {
                case FIELD_USERNAME: {
                    BuilderUtils.buildInOrLikeCondition(
                            search,
                            values -> query.in(fieldMappings.get(FIELD_USERNAME), values),
                            value -> query.like(fieldMappings.get(FIELD_USERNAME), value)
                    );
                    break;
                }
                case FIELD_TYPE: {
                    BuilderUtils.buildInOrEqCondition(
                            search,
                            values -> query.in(fieldMappings.get(FIELD_TYPE), values),
                            value -> query.eq(fieldMappings.get(FIELD_TYPE), value)
                    );
                    break;
                }
                case FIELD_CREATED_BY: {
                    BuilderUtils.buildInOrLikeCondition(
                            search,
                            values -> query.in(fieldMappings.get(FIELD_CREATED_BY), values),
                            value -> query.like(fieldMappings.get(FIELD_CREATED_BY), value)
                    );
                    break;
                }
                case FIELD_CREATED_DATE: {
                    BuilderUtils.buildBetweenCondition(
                            search,
                            (o1, o2) -> query.between(fieldMappings.get(FIELD_CREATED_DATE), o1, o2)
                    );
                    break;
                }
                default: {
                    buildCondition(query, search);
                }
            }
        }
    }
}
