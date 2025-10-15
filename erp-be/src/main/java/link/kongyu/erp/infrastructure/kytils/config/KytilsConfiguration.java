package link.kongyu.erp.infrastructure.kytils.config;

import link.kongyu.kytils.bean.IBeanUtils;
import link.kongyu.kytils.bean.MyBeanUtils;
import link.kongyu.kytils.log.ILogger;
import link.kongyu.kytils.log.loggerFactory.MyLoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/15
 */

@Configuration
public class KytilsConfiguration {

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

    @Bean()
    public IBeanUtils getBeanUtils() {
        return new MyBeanUtils() {
            @Override
            public PropertyDescriptor[] getPropertyDescriptors(Class<?> aClass) {
                return org.springframework.beans.BeanUtils.getPropertyDescriptors(aClass);
            }

            @Override
            public PropertyDescriptor getPropertyDescriptor(Class<?> aClass, String s) {
                return org.springframework.beans.BeanUtils.getPropertyDescriptor(aClass, s);
            }
        };
    }

}
