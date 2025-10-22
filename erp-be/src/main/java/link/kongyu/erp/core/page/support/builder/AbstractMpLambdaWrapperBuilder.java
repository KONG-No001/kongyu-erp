package link.kongyu.erp.core.page.support.builder;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;

import java.util.Map;
import java.util.Set;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/21
 */
public abstract class AbstractMpLambdaWrapperBuilder<T> extends AbstractMpWrapperBuilder<LambdaQueryChainWrapper<T>> {
    protected final Map<String, SFunction<T, ?>> fieldMappings;
    protected final Set<String> allowedFields;

    public AbstractMpLambdaWrapperBuilder(Map<String, SFunction<T, ?>> fieldMappings, Set<String> allowedFields) {
        this.fieldMappings = fieldMappings;
        this.allowedFields = allowedFields;
    }
}
