package link.kongyu.erp.core.page.support.converter;

import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/20
 */
public interface PageRequestConverter<T> {

    T convert(T query, PageRequest request);

}
