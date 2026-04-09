package link.kongyu.erp.infrastructure.logger;

import link.kongyu.erp.common.logger.ILogger;
import link.kongyu.erp.common.logger.loggerFactory.MyLoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */

@Configuration
public class LoggerConfig {
    static {
        MyLoggerFactory.putLoggerFactory(Logger.class, l -> new ILogger() {
            @Override
            public void debug(String format, Object... arguments) {
                l.debug(format, arguments);
            }

            @Override
            public void info(String format, Object... arguments) {
                l.info(format, arguments);
            }

            @Override
            public void warn(String format, Object... arguments) {
                l.warn(format, arguments);
            }

            @Override
            public void error(String format, Object... arguments) {
                l.error(format, arguments);
            }
        });
    }
}
