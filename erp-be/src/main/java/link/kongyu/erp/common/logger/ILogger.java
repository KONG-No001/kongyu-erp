package link.kongyu.erp.common.logger;

/**
 * @author Kongyu
 * @version v1.0.0
 * @since 2025/4/29
 */
public interface ILogger {
    void debug(String format, Object... arguments);

    void info(String format, Object... arguments);

    void warn(String format, Object... arguments);

    void error(String format, Object... arguments);
}
