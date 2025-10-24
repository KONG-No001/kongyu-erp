package link.kongyu.erp.core.page.support.builder.impl;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import link.kongyu.erp.core.page.exception.BuildException;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.PageSort;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.metadata.enums.Operator;
import link.kongyu.erp.core.page.support.builder.AbstractMpLambdaWrapperBuilder;
import link.kongyu.erp.core.page.support.builder.BuilderUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public class GenericMpLambdaWrapperBuilder<T> extends AbstractMpLambdaWrapperBuilder<T> {

    public GenericMpLambdaWrapperBuilder(Map<String, SFunction<T, ?>> fieldMappings, Set<String> allowedFields) {
        super(fieldMappings, allowedFields);
    }

    public GenericMpLambdaWrapperBuilder(Map<String, SFunction<T, ?>> fieldMappings) {
        super(fieldMappings, fieldMappings.keySet());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void buildColumns(LambdaQueryChainWrapper<T> query, PageRequest request) {
        Set<String> columns = request.getColumns();
        if (columns.isEmpty()) {
            // 查询所有允许的字段
            SFunction<T, ?>[] functions = allowedFields
                    .stream()
                    .map(fieldMappings::get)
                    .filter(Objects::nonNull)
                    .toArray(SFunction[]::new);
            query.select(functions);
        }
        else {
            // 只查询指定的字段
            SFunction<T, ?>[] functions = columns
                    .stream()
                    .filter(allowedFields::contains)
                    .map(fieldMappings::get)
                    .toArray(SFunction[]::new);
            query.select(functions);
        }
    }

    @Override
    protected void buildConditions(LambdaQueryChainWrapper<T> query, PageRequest request) {
        Set<PageSearch> searches = request.getSearches();
        if (searches.isEmpty()) {
            return;
        }
        for (PageSearch search : searches) {
            buildCondition(query, search);
        }
    }

    @Override
    protected void buildSorting(LambdaQueryChainWrapper<T> query, PageRequest request) {
        Set<PageSort> sorts = request.getSorts();
        if (sorts.isEmpty()) {
            return;
        }
        for (PageSort sort : sorts) {
            buildSorting(query, sort);
        }
    }

    protected void buildCondition(LambdaQueryChainWrapper<T> query, PageSearch search) {
        if (shouldSkipSearch(search)) { return; }
        try {
            Operator operation = search.getOperation();
            if (operation == null) {
                throw new BuildException("操作符不能为空");
            }
            else if (Operator.BETWEEN == operation) {
                BuilderUtils.buildBetweenCondition(search, (v1, v2) -> query.between(fieldMappings.get(search.getField()), v1, v2));
            }
            else {
                BuilderUtils.buildWrapperByOperator(operation, query, fieldMappings.get(search.getField()), search.getValue());
            }

        }
        catch (InvocationTargetException | IllegalAccessException e) {
            throw new BuildException(
                    String.format("构建查询条件失败 [字段:%s, 操作:%s]", search.getField(), search.getOperation()),
                    e
            );
        }
    }

    protected void buildSorting(LambdaQueryChainWrapper<T> query, PageSort sort) {
        if (shouldSkipSort(sort)) { return; }
        Direction direction = sort.getDirection();
        if (direction == null) { direction = Direction.ASC; }
        try {
            BuilderUtils.buildWrapperByDirection(direction, query, fieldMappings.get(sort.getField()));
        }
        catch (InvocationTargetException | IllegalAccessException e) {
            throw new BuildException(
                    String.format("构建排序条件失败 [字段:%s, 操作:%s]", sort.getField(), direction),
                    e
            );
        }
    }

    protected boolean shouldSkipSearch(PageSearch search) {
        String field = search.getField();
        Operator operation = search.getOperation();
        Object value = search.getValue();

        if (!fieldMappings.containsKey(field)) {
            return true;
        }
        if (operation == null || Operator.NULL == operation) {
            return true;
        }
        return value == null;
    }

    protected boolean shouldSkipSort(PageSort sort) {
        String field = sort.getField();
        return !fieldMappings.containsKey(field);
    }
}
