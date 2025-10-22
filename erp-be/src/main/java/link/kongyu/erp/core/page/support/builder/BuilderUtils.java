package link.kongyu.erp.core.page.support.builder;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.extension.conditions.AbstractChainWrapper;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.metadata.enums.Operator;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static link.kongyu.erp.core.page.constants.WrapperMappings.DIRECTION_WRAPPER_MAPPINGS;
import static link.kongyu.erp.core.page.constants.WrapperMappings.OPERATOR_WRAPPER_MAPPINGS;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public class BuilderUtils {

    public static <T, R, Children extends AbstractChainWrapper<T, R, Children, Param>, Param extends AbstractWrapper<T, R, Param>> void buildWrapperByOperator(
            Operator operator,
            AbstractChainWrapper<T, R, Children, Param> wrapper,
            Object... args
    ) throws InvocationTargetException, IllegalAccessException {
        if (OPERATOR_WRAPPER_MAPPINGS.containsKey(operator)) {
            OPERATOR_WRAPPER_MAPPINGS.get(operator).invoke(wrapper, args);
        }
    }


    public static <T, R, Children extends AbstractChainWrapper<T, R, Children, Param>, Param extends AbstractWrapper<T, R, Param>> void buildWrapperByDirection(
            Direction direction,
            AbstractChainWrapper<T, R, Children, Param> wrapper,
            Object... args
    ) throws InvocationTargetException, IllegalAccessException {
        if (DIRECTION_WRAPPER_MAPPINGS.containsKey(direction)) {
            DIRECTION_WRAPPER_MAPPINGS.get(direction).invoke(wrapper, args);
        }
    }

    public static void buildInOrLikeCondition(PageSearch search, Consumer<List<String>> inFun, Consumer<String> likeFun) throws IllegalArgumentException {
        Object obj = search.getValue();
        if (obj == null) {
            throw new IllegalArgumentException("字段值不能为空");
        }
        if (obj instanceof Collection) {
            inFun.accept(((Collection<?>) obj).stream().map(Object::toString).collect(Collectors.toList()));
        }
        else if (obj instanceof String) {
            String value = ((String) obj).trim();
            if (value.isEmpty()) {
                throw new IllegalArgumentException("字段值不能为空");
            }
            if (value.contains(",") || value.contains(" ")) {
                // 多值查询
                List<String> values = Arrays
                        .stream(value.replace(" ", ",").split(","))
                        .map(String::trim)
                        .filter(str -> !StringUtils.isEmpty(str))
                        .collect(Collectors.toList());
                if (!values.isEmpty()) {
                    inFun.accept(values);
                }
            }
            else {
                // 模糊查询
                likeFun.accept(value.trim());
            }
        }
        else {
            likeFun.accept(obj.toString());
        }
    }

    public static void buildInOrEqCondition(PageSearch search, Consumer<List<Object>> inFun, Consumer<Object> eqFun) {
        if (search.getValue() instanceof Collection) {
            inFun.accept(new ArrayList<>((Collection<?>) search.getValue()));
        }
        else {
            eqFun.accept(search.getValue());
        }
    }

    public static void buildBetweenCondition(PageSearch search, BiConsumer<Object, Object> fun) {
        if (((Collection<?>) search.getValue()).size() != 2) {
            throw new IllegalArgumentException("字段值数量必须为2");
        }
        Iterator<?> iterator = ((Collection<?>) search.getValue()).iterator();
        fun.accept(iterator.next(), iterator.next());
    }
}
