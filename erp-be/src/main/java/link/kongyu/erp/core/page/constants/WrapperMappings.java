package link.kongyu.erp.core.page.constants;

import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.metadata.enums.Operator;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/22
 */
public class WrapperMappings {
    public static final Map<Operator, Method> OPERATOR_WRAPPER_MAPPINGS = new HashMap<>();
    public static final Map<Direction, Method> DIRECTION_WRAPPER_MAPPINGS = new HashMap<>();

    static {
        try {
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.EQ, AbstractChainWrapper.class.getMethod("eq", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.NE, AbstractChainWrapper.class.getMethod("ne", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.LIKE, AbstractChainWrapper.class.getMethod("like", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.IN, AbstractChainWrapper.class.getMethod("in", Object.class, Collection.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.GT, AbstractChainWrapper.class.getMethod("gt", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.GE, AbstractChainWrapper.class.getMethod("ge", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.LT, AbstractChainWrapper.class.getMethod("lt", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.LE, AbstractChainWrapper.class.getMethod("le", Object.class, Object.class));
            OPERATOR_WRAPPER_MAPPINGS.put(Operator.BETWEEN, AbstractChainWrapper.class.getMethod("between", Object.class, Object.class, Object.class));

            DIRECTION_WRAPPER_MAPPINGS.put(Direction.ASC, AbstractChainWrapper.class.getMethod("orderByAsc", Object.class));
            DIRECTION_WRAPPER_MAPPINGS.put(Direction.DESC, AbstractChainWrapper.class.getMethod("orderByDesc", Object.class));

        }
        catch (NoSuchMethodException exception) {
            exception.printStackTrace();
        }
    }
}
