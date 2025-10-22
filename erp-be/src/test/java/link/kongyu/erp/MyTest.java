package link.kongyu.erp;

import com.baomidou.mybatisplus.core.conditions.interfaces.Compare;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;

import java.lang.reflect.Method;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public class MyTest {
    public static void main(String[] args) throws NoSuchMethodException {
        /*Method[] methods = Compare.class.getMethods();

        for (Method method : methods) {

            System.out.println(method.getName());

        }*/
        Method eq = AbstractChainWrapper.class.getMethod("eq", Object.class, Object.class);
    }
}
