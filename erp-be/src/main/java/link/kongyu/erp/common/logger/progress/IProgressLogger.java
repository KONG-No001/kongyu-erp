package link.kongyu.erp.common.logger.progress;

import link.kongyu.erp.common.logger.ILogger;

/**
 * @author Kongyu
 * @version v1.0.0
 * @since 2025/4/29
 */
public interface IProgressLogger extends ILogger {

    void increment();

    void increment(String format, Object... obj);
}
