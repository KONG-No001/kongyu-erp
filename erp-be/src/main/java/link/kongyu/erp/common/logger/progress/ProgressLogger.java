package link.kongyu.erp.common.logger.progress;


import link.kongyu.erp.common.logger.ILogger;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Kongyu
 * @version v1.0.0
 * @since 2025/4/29
 */
public class ProgressLogger implements IProgressLogger {

    private final ILogger logger;
    private final long total;
    private final long cd;
    private final AtomicLong count = new AtomicLong(0);
    private final AtomicLong millis = new AtomicLong(0);

    public ProgressLogger(ILogger logger, long total, long cd) {
        this.logger = logger;
        this.total = total;
        this.cd = cd;
    }


    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(getFormat(format), getObjects(arguments));
    }

    @Override
    public void info(String format, Object... arguments) {
        logger.info(getFormat(format), getObjects(arguments));
    }

    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(getFormat(format), getObjects(arguments));
    }

    @Override
    public void error(String format, Object... arguments) {
        logger.error(getFormat(format), getObjects(arguments));
    }

    @Override
    public void increment() {
        increment(null, (Object[]) null);
    }

    @Override
    public void increment(String format, Object... obj) {
        count.incrementAndGet();

        long previousMillis = millis.get();
        if (cd > 0) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - previousMillis > cd) {
                if (millis.compareAndSet(previousMillis, currentTimeMillis)) {
                    info(format, obj);
                }
            }
        }
        else {
            info(format, obj);
        }
    }

    private String getFormat(String format) {
        return "{}/{}" + (format == null ? "" : " - " + format);
    }

    private Object[] getObjects(Object[] arguments) {
        Object[] defaultObjects = new Object[]{count.get(), total};
        if (arguments == null || arguments.length == 0) {
            return defaultObjects;
        }

        Object[] objects = new Object[arguments.length + 2];
        objects[0] = defaultObjects[0];
        objects[1] = defaultObjects[1];
        System.arraycopy(arguments, 0, objects, 2, arguments.length);
        return objects;
    }
}
