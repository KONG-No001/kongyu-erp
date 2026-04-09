package link.kongyu.erp.modules.sys.support.compiler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.enums.Operator;
import link.kongyu.erp.core.page.support.compiler.MpQueryCompilerAbstract;
import link.kongyu.erp.core.page.support.compiler.StaticMpQueryCompiler;
import link.kongyu.erp.modules.sys.entity.Role;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Component
public class RoleQueryCompiler extends MpQueryCompilerAbstract<Role> {

    private final Map<String, BiConsumer<PageSearch, QueryWrapper<Role>>> conditionGenerators = new HashMap<>();

    @PostConstruct
    public void init() {
        register("roleName", Operator.IN_OR_LIKE);
        register("role_name", Operator.IN_OR_LIKE);
        register("parentId", Operator.IN_OR_EQ);
        register("parent_id", Operator.IN_OR_EQ);
        register("enabled", Operator.IN_OR_EQ);
        register("createdBy", Operator.IN_OR_EQ);
        register("created_by", Operator.IN_OR_EQ);
        register("createdDate", Operator.BETWEEN);
        register("created_date", Operator.BETWEEN);
    }

    @Override
    public void compileCondition(PageRequest request, QueryWrapper<Role> wrapper) {
        if (request.getSearches() == null || request.getSearches().isEmpty()) {
            return;
        }
        for (PageSearch search : request.getSearches()) {
            BiConsumer<PageSearch, QueryWrapper<Role>> generator = conditionGenerators.get(search.getField());
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
