package link.kongyu.erp.common.logger.loggerFactory;

import link.kongyu.erp.common.logger.ILogger;

/**
 * @author Kongyu
 * @version v1.0.0
 * @since 2025/4/29
 */
public interface ILoggerFactory<T> {
    ILogger getLogger(T logger);
}
