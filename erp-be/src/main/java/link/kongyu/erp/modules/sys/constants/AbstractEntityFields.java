package link.kongyu.erp.modules.sys.constants;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import org.springframework.beans.FatalBeanException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/2/11
 */
public abstract class AbstractEntityFields<T> {

    protected abstract Method getReadMethod(String name);

    protected SFunction<T, ?> getSFunction(String name) {
        Method readMethod = getReadMethod(name);
        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
            readMethod.setAccessible(true);
        }
        return t-> {
            try {
                return readMethod.invoke(t);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new FatalBeanException("无法访问属性 '" + name + "'", e);
            }
        };
    }
}
