package link.kongyu.erp.core.page.support.builder;

import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public abstract class AbstractMpWrapperBuilder<T> implements QueryBuilder<T> {

    protected abstract void buildColumns(T query, PageRequest request);

    protected abstract void buildConditions(T query, PageRequest request);

    protected abstract void buildSorting(T query, PageRequest request);

    @Override
    public T buildQuery(T query, PageRequest request) {
        buildColumns(query, request);
        buildConditions(query, request);
        buildSorting(query, request);
        return query;
    }
}
