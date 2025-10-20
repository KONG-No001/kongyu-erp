package link.kongyu.erp.core.page.support.converter;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.PageSort;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.metadata.enums.Operator;

import java.util.Collection;
import java.util.Set;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/20
 */

public class BuildMpQueryChainWrapperConverter {

    public static <T> QueryChainWrapper<T> buildQueryWrapper(QueryChainWrapper<T> query, PageRequest request) {
        BuildMpQueryChainWrapperConverter.buildQueryColumns(query, request);
        BuildMpQueryChainWrapperConverter.buildQueryConditions(query, request);
        BuildMpQueryChainWrapperConverter.buildSortConditions(query, request);
        return query;
    }

    private static <T> void buildQueryColumns(QueryChainWrapper<T> query, PageRequest request) {
        if (query == null) { return; }
        Set<String> columns = request.getColumns();
        if (!columns.isEmpty()) {
            query.select(columns.toArray(new String[0]));
        }
    }

    private static <T> void buildQueryConditions(QueryChainWrapper<T> query, PageRequest request) {
        if (query == null) { return; }
        Set<PageSearch> searches = request.getSearches();
        if (!searches.isEmpty()) {
            for (PageSearch search : searches) {
                search.validate();
                String field = search.getField();
                Operator operation = search.getOperation();
                Object value = search.getValue();

                switch (operation) {
                    case EQ:
                        query.eq(field, value);
                        break;
                    case NE:
                        query.ne(field, value);
                        break;
                    case LIKE:
                        query.like(field, value);
                        break;
                    case GT:
                        query.gt(field, value);
                        break;
                    case GE:
                        query.ge(field, value);
                        break;
                    case LT:
                        query.lt(field, value);
                        break;
                    case LE:
                        query.le(field, value);
                        break;
                    case IN:
                        query.in(field, (Collection<?>) value);
                        break;
                    case BETWEEN:
                        Object[] objects = ((Collection<?>) value).toArray();
                        query.between(field, objects[0], objects[1]);
                        break;
                    case NONE:
                        break;
                }
            }
        }
    }

    private static <T> void buildSortConditions(QueryChainWrapper<T> query, PageRequest request) {
        if (query == null) { return; }
        Set<PageSort> sorts = request.getSorts();
        if (!sorts.isEmpty()) {
            for (PageSort sort : sorts) {
                sort.validate();
                String field = sort.getField();
                if (Direction.DESC.equals(sort.getDirection())) {
                    query.orderByDesc(field);
                }
                else {
                    query.orderByAsc(field);
                }
            }
        }
    }

}
