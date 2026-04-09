package link.kongyu.erp.core.page.support.compiler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import link.kongyu.erp.common.utils.StringUtils;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.metadata.PageSearch;
import link.kongyu.erp.core.page.metadata.PageSort;
import link.kongyu.erp.core.page.metadata.enums.Direction;
import link.kongyu.erp.core.page.metadata.enums.Operator;

import java.util.Collection;
import java.util.List;

/**
 * 通用查询构造器 (基于标准 QueryWrapper，无需泛型与反射)
 * 将前端传来的 JSON 协议 (PageRequest) 直接翻译成 MyBatis-Plus 的 QueryWrapper
 */
public class StaticMpQueryCompiler {

    /**
     * 将 PageRequest 编译为 MyBatis-Plus 的 QueryWrapper
     */
    public static <T> QueryWrapper<T> compile(PageRequest request) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        select(request, wrapper);

        compileCondition(request, wrapper);

        sort(request, wrapper);

        return wrapper;
    }

    public static <T> void select(PageRequest request, QueryWrapper<T> wrapper) {
        if (request.getColumns() != null && !request.getColumns().isEmpty()) {
            String[] selectCols = request
                    .getColumns().stream()
                    .map(StringUtils::camelToUnderline)
                    .toArray(String[]::new);
            wrapper.select(selectCols);
        }
    }

    public static <T> void compileCondition(PageRequest request, QueryWrapper<T> wrapper) {
        // 2. 编译 WHERE 条件
        if (request.getSearches() != null && !request.getSearches().isEmpty()) {
            for (PageSearch search : request.getSearches()) {
                compileCondition(search, wrapper);
            }
        }
    }

    public static <T> void sort(PageRequest request, QueryWrapper<T> wrapper) {
        if (request.getSorts() != null && !request.getSorts().isEmpty()) {
            for (PageSort sort : request.getSorts()) {
                String dbColumn = StringUtils.camelToUnderline(sort.getField());
                if (sort.getDirection() == Direction.DESC) {
                    wrapper.orderByDesc(dbColumn);
                }
                else {
                    wrapper.orderByAsc(dbColumn);
                }
            }
        }
    }

    /**
     * 编译具体的查询条件
     */
    public static <T> void compileCondition(PageSearch search, QueryWrapper<T> wrapper) {
        String field = search.getField();
        Object value = search.getValue();
        Operator operation = search.getOperation();

        if (field == null || field.trim().isEmpty() || operation == null || operation == Operator.NULL) {
            return;
        }

        // 驼峰命名转换为数据库的下划线命名，如 userName -> user_name
        String dbColumn = StringUtils.camelToUnderline(field);

        switch (operation) {
            case EQ:
                wrapper.eq(dbColumn, value);
                break;
            case NE:
                wrapper.ne(dbColumn, value);
                break;
            case LIKE:
                wrapper.like(value != null, dbColumn, value);
                break;
            case IN:
                if (value instanceof Collection && !((Collection<?>) value).isEmpty()) {
                    wrapper.in(dbColumn, (Collection<?>) value);
                }
                break;
            case GT:
                wrapper.gt(dbColumn, value);
                break;
            case GE:
                wrapper.ge(dbColumn, value);
                break;
            case LT:
                wrapper.lt(dbColumn, value);
                break;
            case LE:
                wrapper.le(dbColumn, value);
                break;
            case BETWEEN:
                if (value instanceof List && ((List<?>) value).size() == 2) {
                    List<?> list = (List<?>) value;
                    wrapper.between(dbColumn, list.get(0), list.get(1));
                }
                break;
            case IN_OR_EQ:
                if (value instanceof Collection && !((Collection<?>) value).isEmpty()) {
                    wrapper.in(dbColumn, (Collection<?>) value);
                }
                else if (value != null) {
                    wrapper.eq(dbColumn, value);
                }
                break;
            case IN_OR_LIKE:
                if (value instanceof Collection && !((Collection<?>) value).isEmpty()) {
                    wrapper.in(dbColumn, (Collection<?>) value);
                }
                else if (value != null) {
                    wrapper.like(dbColumn, value);
                }
                break;
            case IN_OR_LEFT_LIKE:
                if (value instanceof Collection && !((Collection<?>) value).isEmpty()) {
                    wrapper.in(dbColumn, (Collection<?>) value);
                }
                else if (value != null) {
                    wrapper.likeLeft(dbColumn, value);
                }
                break;
            case IN_OR_RIGHT_LIKE:
                if (value instanceof Collection && !((Collection<?>) value).isEmpty()) {
                    wrapper.in(dbColumn, (Collection<?>) value);
                }
                else if (value != null) {
                    wrapper.likeRight(dbColumn, value);
                }
                break;
            default:
                break;
        }
    }
}
