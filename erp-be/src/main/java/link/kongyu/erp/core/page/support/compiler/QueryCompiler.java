package link.kongyu.erp.core.page.support.compiler;

import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/9
 */
public interface QueryCompiler<T> {
    T compile(PageRequest request);
}
