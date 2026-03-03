package link.kongyu.erp.core.page.support.builder;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.utils.MyBeanUtils;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/2/11
 */
public class FieldMapping<T> {

    private final Class<T> clazz;

    private final Map<String, SFunction<T, ?>> mappings = new ConcurrentHashMap<>();

    public FieldMapping(Class<T> clazz) {
        this.clazz = clazz;
    }

    public SFunction<T, ?> get(String name) {
        SFunction<T, ?> tsFunction = this.mappings.get(name);
        if (tsFunction == null) {
            tsFunction = get(this.clazz, name);
            this.mappings.put(name, tsFunction);
        }
        return tsFunction;
    }

    protected SFunction<T, ?> get(Class<T> clazz, String name) {
        PropertyDescriptor propertyDescriptor = MyBeanUtils.getPropertyDescriptor(clazz, name);
        if (propertyDescriptor == null) {
            throw new RuntimeException();
        }
        Method readMethod = propertyDescriptor.getReadMethod();
        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
        }
        return t -> {
            try {
                return readMethod.invoke(t);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new FatalBeanException("无法访问属性 '" + name + "'", e);
            }
        };
    }
}
