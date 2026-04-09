package link.kongyu.erp.common.logger.loggerFactory;

import link.kongyu.erp.common.logger.ILogger;
import link.kongyu.erp.common.logger.progress.IProgressLogger;
import link.kongyu.erp.common.logger.progress.ProgressLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kongyu
 * @version v1.0.0
 * @since 2025/4/29
 */
public class MyLoggerFactory {
    private static final Map<Class<?>, ILoggerFactory<?>> LOGGER_FACTORY_MAP = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> ILogger getLogger(T logger) {
        ILoggerFactory<T> factory = (ILoggerFactory<T>) getLoggerFactory(logger.getClass());
        return factory.getLogger(logger);
    }

    public static <T> IProgressLogger getProgressLogger(T logger, long total, long cd) {
        return new ProgressLogger(getLogger(logger), total, cd);
    }

    public static <T> void putLoggerFactory(Class<T> clazz, ILoggerFactory<T> loggerFactory) {
        LOGGER_FACTORY_MAP.put(clazz, loggerFactory);
    }

    public static <T> ILoggerFactory<?> getLoggerFactory(Class<T> clazz) {
        return LOGGER_FACTORY_MAP.entrySet().stream().filter(t -> t.getKey().isAssignableFrom(clazz)).min((o1, o2) -> {
            Class<?> k1 = o1.getKey();
            Class<?> k2 = o2.getKey();
            if (k1 == k2) { return 0; }
            return k1.isAssignableFrom(k2) ? 1 : -1;
        }).map(Map.Entry::getValue).orElse(null);
    }
}
