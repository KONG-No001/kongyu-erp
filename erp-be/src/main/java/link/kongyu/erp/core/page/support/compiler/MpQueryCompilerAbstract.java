package link.kongyu.erp.core.page.support.compiler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import link.kongyu.erp.core.page.metadata.PageRequest;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/9
 */
public abstract class MpQueryCompilerAbstract<T> implements QueryCompiler<QueryWrapper<T>> {
    public QueryWrapper<T> compile(PageRequest request) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        select(request, wrapper);

        compileCondition(request, wrapper);

        sort(request, wrapper);

        return wrapper;
    }

    public void select(PageRequest request, QueryWrapper<T> wrapper) {
        StaticMpQueryCompiler.select(request, wrapper);
    }

    public void compileCondition(PageRequest request, QueryWrapper<T> wrapper) {
        StaticMpQueryCompiler.compileCondition(request, wrapper);
    }

    public void sort(PageRequest request, QueryWrapper<T> wrapper) {
        StaticMpQueryCompiler.sort(request, wrapper);
    }

}
