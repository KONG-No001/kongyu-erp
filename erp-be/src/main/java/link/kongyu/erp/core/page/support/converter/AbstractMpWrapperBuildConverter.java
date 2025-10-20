package link.kongyu.erp.core.page.support.converter;

import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.PageSort;

import java.util.Set;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/20
 */
public abstract class AbstractMpWrapperBuildConverter<T> implements BuildQueryConverter<T> {

    protected abstract void buildQueryColumn(T query, String column);

    protected abstract void buildQueryCondition(T query, PageSearch search);

    protected abstract void buildSortCondition(T query, PageSort sort);

    protected void buildQueryColumns(T query, PageRequest request) {
        if (query == null) { return; }
        Set<String> columns = request.getColumns();
        if (!columns.isEmpty()) {
            for (String column : columns) {
                buildQueryColumn(query, column);
            }
        }
    }

    protected void buildQueryConditions(T query, PageRequest request) {
        if (query == null) { return; }
        Set<PageSearch> searches = request.getSearches();
        if (!searches.isEmpty()) {
            for (PageSearch search : searches) {
                search.validate();
                buildQueryCondition(query, search);
            }
        }
    }

    protected void buildSortConditions(T query, PageRequest request) {
        if (query == null) { return; }
        Set<PageSort> sorts = request.getSorts();
        if (!sorts.isEmpty()) {
            for (PageSort sort : sorts) {
                sort.validate();
                buildSortCondition(query, sort);
            }
        }
    }

    @Override
    public T buildQuery(T query, PageRequest request) {
        buildQueryColumns(query, request);
        buildQueryConditions(query, request);
        buildSortConditions(query, request);
        return query;
    }

}
