package link.kongyu.erp.core.page.support.builder;

import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public interface QueryBuilder<T> {
    T buildQuery(T query, PageRequest request);
}
