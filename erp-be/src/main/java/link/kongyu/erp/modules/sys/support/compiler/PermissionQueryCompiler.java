package link.kongyu.erp.modules.sys.support.compiler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.enums.Operator;
import link.kongyu.erp.core.page.support.compiler.MpQueryCompilerAbstract;
import link.kongyu.erp.core.page.support.compiler.StaticMpQueryCompiler;
import link.kongyu.erp.modules.sys.entity.Permission;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class PermissionQueryCompiler extends MpQueryCompilerAbstract<Permission> {

    private final Map<String, BiConsumer<PageSearch, QueryWrapper<Permission>>> conditionGenerators = new HashMap<>();

    @PostConstruct
    public void init() {
        register("businessGroup", Operator.IN_OR_LIKE);
        register("business_group", Operator.IN_OR_LIKE);
        register("permissionName", Operator.IN_OR_LIKE);
        register("permission_name", Operator.IN_OR_LIKE);
        register("type", Operator.IN_OR_EQ);
        register("accessResource", Operator.IN_OR_LIKE);
        register("access_resource", Operator.IN_OR_LIKE);
        register("enabled", Operator.IN_OR_EQ);
        register("createdBy", Operator.IN_OR_EQ);
        register("created_by", Operator.IN_OR_EQ);
        register("createdDate", Operator.BETWEEN);
        register("created_date", Operator.BETWEEN);
    }

    @Override
    public void compileCondition(PageRequest request, QueryWrapper<Permission> wrapper) {
        if (request.getSearches() == null || request.getSearches().isEmpty()) {
            return;
        }
        for (PageSearch search : request.getSearches()) {
            BiConsumer<PageSearch, QueryWrapper<Permission>> generator = conditionGenerators.get(search.getField());
            if (generator == null) {
                throw new IllegalArgumentException("不支持的字段: " + search.getField());
            }
            generator.accept(search, wrapper);
        }
    }

    private void register(String field, Operator operator) {
        conditionGenerators.put(field, (search, wrapper) -> {
            search.setOperation(operator);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
    }
}
