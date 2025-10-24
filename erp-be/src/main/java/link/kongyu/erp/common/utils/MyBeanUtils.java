package link.kongyu.erp.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */
public class MyBeanUtils extends BeanUtils {

    public static void mergeProperties(Object source, Object target, String... ignoreProperties) throws BeansException {
        mergeProperties(source, target, ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
    }

    public static void mergeProperties(Object source, Object target, List<String> ignoreList) {
        Assert.notNull(source, "参数 Source 不能为空");
        Assert.notNull(target, "参数 Target 不能为空");

        Class<?> actualEditable = source.getClass();

        PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
        for (PropertyDescriptor sourcePd : sourcePds) {
            Method readMethod = sourcePd.getReadMethod();
            if (readMethod != null && (ignoreList == null || !ignoreList.contains(sourcePd.getName()))) {
                PropertyDescriptor targetPd = getPropertyDescriptor(target.getClass(), sourcePd.getName());
                if (targetPd != null) {
                    Method writeMethod = targetPd.getWriteMethod();
                    if (writeMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            if (value != null) {
                                writeMethod.invoke(target, value);
                            }
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException("无法将属性 '" + sourcePd.getName() + "' 从源合并到目标", ex);
                        }
                    }
                }
            }
        }
    }

    public void setProperty(Object bean, String property, Object value) throws BeansException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean.getClass(), property);
        if (propertyDescriptor != null) {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                writeMethod.setAccessible(true);
            }
            try {
                writeMethod.invoke(bean, value);
            }
            catch (IllegalAccessException | InvocationTargetException e) {
                throw new FatalBeanException("无法设置属性'" + property + "'", e);
            }
        }
    }

    public Object getProperty(Object bean, String property) throws BeansException {
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(bean.getClass(), property);
        if (propertyDescriptor != null) {
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null) {
                if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                    readMethod.setAccessible(true);
                }
                try {
                    return readMethod.invoke(bean);
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    throw new FatalBeanException("无法获取属性'" + property + "'", e);
                }
            }
        }
        return null;
    }

    public <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws BeansException {
        T bean = newInstance(clazz);

        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                try {
                    setProperty(bean, key, value);
                }
                catch (BeansException e) {
                    throw new FatalBeanException("从 Map 转换到'" + clazz.getName() + "'失败", e);
                }
            }
        }
        return bean;
    }

    public Map<String, Object> beanToMap(Object bean) throws BeansException {
        Class<?> clazz = bean.getClass();
        Map<String, Object> map = new HashMap<>();

        for (Field declaredField : clazz.getDeclaredFields()) {
            String name = declaredField.getName();
            if ("class".equals(name)) {
                continue;
            }
            try {
                map.put(name, getProperty(bean, name));
            }
            catch (BeansException e) {
                throw new FatalBeanException("转换Map失败", e);
            }
        }
        return map;
    }

    private <T> T newInstance(Class<T> clazz) throws BeansException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        }
        catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new FatalBeanException("对象'" + clazz.getName() + "'无法实例化", e);
        }
    }

}
