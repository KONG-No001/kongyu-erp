package link.kongyu.erp.core.page.service;

import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/14
 */
public interface QueryPageIService<T> {

    T findOfPage(final PageRequest pageRequest);

}
