package link.kongyu.erp.modules.sys.support.compiler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.enums.Operator;
import link.kongyu.erp.core.page.support.compiler.MpQueryCompilerAbstract;
import link.kongyu.erp.core.page.support.compiler.StaticMpQueryCompiler;
import link.kongyu.erp.modules.sys.entity.Account;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/9
 */

@Component
public class AccountQueryCompiler extends MpQueryCompilerAbstract<Account> {

    private final Map<String, BiConsumer<PageSearch, QueryWrapper<Account>>> conditionGenerators = new HashMap<>();

    @PostConstruct
    public void init() {
        conditionGenerators.put("username", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_LIKE);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("user_name", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_LIKE);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("enabled", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_EQ);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("createdBy", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_EQ);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("type", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_EQ);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("created_by", (search, wrapper) -> {
            search.setOperation(Operator.IN_OR_EQ);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("created_date", (search, wrapper) -> {
            search.setOperation(Operator.BETWEEN);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
        conditionGenerators.put("createdDate", (search, wrapper) -> {
            search.setOperation(Operator.BETWEEN);
            StaticMpQueryCompiler.compileCondition(search, wrapper);
        });
    }

    @Override
    public void compileCondition(PageRequest request, QueryWrapper<Account> wrapper) {
        if (request.getSearches() != null && !request.getSearches().isEmpty()) {
            for (PageSearch search : request.getSearches()) {
                String field = search.getField();
                BiConsumer<PageSearch, QueryWrapper<Account>> generator = conditionGenerators.get(field);
                if (generator == null) {
                    throw new IllegalArgumentException("不支持的字段: " + field);
                }
                generator.accept(search, wrapper);
            }
        }
    }


}
