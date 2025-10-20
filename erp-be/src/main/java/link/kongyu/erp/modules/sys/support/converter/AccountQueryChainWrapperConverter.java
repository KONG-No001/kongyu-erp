package link.kongyu.erp.modules.sys.support.converter;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.PageSort;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.support.converter.AbstractMpWrapperBuildConverter;
import link.kongyu.erp.core.page.support.converter.BuildQueryConverter;
import link.kongyu.erp.modules.sys.entity.Account;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/20
 */

@Component
public class AccountQueryChainWrapperConverter extends AbstractMpWrapperBuildConverter<LambdaQueryChainWrapper<Account>> implements BuildQueryConverter<LambdaQueryChainWrapper<Account>> {

    /* 查询字段名 */
    private static final String FIELD_ID = "id";
    private static final String FIELD_CREATED_BY = "createdBy";
    private static final String FIELD_CREATED_DATE = "createdDate";
    private static final String FIELD_UPDATED_BY = "updatedBy";
    private static final String FIELD_UPDATED_DATE = "updatedDate";
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_ENABLED = "enabled";

    private static final Map<String, SFunction<Account, ?>> FIELD_FUNCTION_MAP = new HashMap<>();

    static {
        FIELD_FUNCTION_MAP.put(FIELD_ID, Account::getId);
        FIELD_FUNCTION_MAP.put(FIELD_CREATED_BY, Account::getCreatedBy);
        FIELD_FUNCTION_MAP.put(FIELD_CREATED_DATE, Account::getCreatedDate);
        FIELD_FUNCTION_MAP.put(FIELD_UPDATED_BY, Account::getUpdatedBy);
        FIELD_FUNCTION_MAP.put(FIELD_UPDATED_DATE, Account::getUpdatedDate);
        FIELD_FUNCTION_MAP.put(FIELD_USERNAME, Account::getUsername);
        FIELD_FUNCTION_MAP.put(FIELD_TYPE, Account::getType);
        FIELD_FUNCTION_MAP.put(FIELD_ENABLED, Account::getEnabled);
    }

    @Override
    protected void buildQueryColumn(LambdaQueryChainWrapper<Account> query, String column) {
    }

    @Override
    protected void buildQueryCondition(LambdaQueryChainWrapper<Account> query, PageSearch search) {
        // 实行字段严格控制，只能指定允许的查询
        String field = search.getField();
        switch (field) {
            case FIELD_USERNAME: {
                buildOfInOrLikeCondition(
                        search,
                        values -> query.in(FIELD_FUNCTION_MAP.get(FIELD_USERNAME), values),
                        value -> query.like(FIELD_FUNCTION_MAP.get(FIELD_USERNAME), value)
                );
                break;
            }
            case FIELD_TYPE: {
                buildInCondition(
                        search,
                        values -> query.in(FIELD_FUNCTION_MAP.get(FIELD_TYPE), values)
                );
                break;
            }
            case FIELD_CREATED_BY: {
                buildOfInOrLikeCondition(
                        search,
                        values -> query.in(FIELD_FUNCTION_MAP.get(FIELD_CREATED_BY), values),
                        value -> query.like(FIELD_FUNCTION_MAP.get(FIELD_CREATED_BY), value)
                );
                break;
            }
            case FIELD_CREATED_DATE: {
                if (((Collection<?>) search.getValue()).size() != 2) {
                    throw new IllegalArgumentException(FIELD_CREATED_DATE + "的数量必须为2");
                }

                List<String> values = ((Collection<?>) search.getValue())
                        .stream()
                        .map(t -> {
                            if (!(t instanceof String)) {
                                throw new IllegalArgumentException(FIELD_CREATED_DATE + "的值必须是字符串集合");
                            }
                            return ((String) t).trim();
                        })
                        .filter(str -> !StringUtils.isEmpty(str))
                        .collect(Collectors.toList());

                query.between(FIELD_FUNCTION_MAP.get(FIELD_CREATED_DATE), values.get(0), values.get(1));
                break;
            }
        }
    }

    @Override
    protected void buildSortCondition(LambdaQueryChainWrapper<Account> query, PageSort sort) {
        // 实行字段严格控制，只能允许的字段排序
        String field = sort.getField();
        switch (field) {
            case FIELD_CREATED_BY:
            case FIELD_CREATED_DATE:
            case FIELD_USERNAME:
            case FIELD_TYPE:
            case FIELD_ENABLED:
                if (Direction.DESC.equals(sort.getDirection())) {
                    query.orderByDesc(FIELD_FUNCTION_MAP.get(field));
                }
                else {
                    query.orderByAsc(FIELD_FUNCTION_MAP.get(field));
                }
                break;
        }
    }

    @Override
    protected void buildQueryColumns(LambdaQueryChainWrapper<Account> query, PageRequest request) {
        Set<String> columns = request.getColumns();
        if (columns.isEmpty()) {
            columns.add(FIELD_ID);
            columns.add(FIELD_CREATED_BY);
            columns.add(FIELD_CREATED_DATE);
            columns.add(FIELD_USERNAME);
            columns.add(FIELD_TYPE);
            columns.add(FIELD_ENABLED);
        }



    }

    private void buildInCondition(PageSearch search, Consumer<List<?>> inFun) {
        List<?> values;
        if (search.getValue() instanceof Collection) {
            values = new ArrayList<>((Collection<?>) search.getValue());
        }
        else {
            values = Collections.singletonList(search.getValue());
        }
        inFun.accept(values);
    }

    private void buildOfInOrLikeCondition(PageSearch search, Consumer<List<String>> inFun, Consumer<String> likeFun) {
        if (!(search.getValue() instanceof String)) {
            throw new IllegalArgumentException(search.getValue() + "的值必须是字符串");
        }
        String value = (String) search.getValue();
        if (!StringUtils.isEmpty(value)) {
            if (value.contains(",") || value.contains(" ")) {
                // 多值查询
                List<String> values = Arrays
                        .stream(value.replace(" ", ",").split(","))
                        .map(String::trim)
                        .filter(str -> !StringUtils.isEmpty(str))
                        .collect(Collectors.toList());
                if (!values.isEmpty()) {
                    inFun.accept(values);
                }
            }
            else {
                // 模糊查询
                likeFun.accept(value.trim());
            }
        }
    }
}
