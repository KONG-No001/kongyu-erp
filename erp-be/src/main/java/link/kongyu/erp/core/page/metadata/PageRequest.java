package link.kongyu.erp.core.page.metadata;

import link.kongyu.erp.core.page.metadata.enums.Operator;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 页面请求对象
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/27
 */
public class PageRequest implements Cloneable, Serializable {

    private static final long serialVersionUID = 2104314315782542555L;

    /**
     * 搜索字段
     */
    @Setter
    protected Set<PageSearch> searches;

    /**
     * 排序字段
     */
    @Setter
    protected LinkedHashSet<PageSort> sorts;

    /**
     * 页数
     */
    @Setter
    @Getter
    protected Integer pageNum = 1;

    /**
     * 页面大小
     */
    @Setter
    @Getter
    protected Integer pageSize = 30;


    /**
     * 深度复制当前对象
     *
     * @return 当前对象副本
     * @throws CloneNotSupportedException 不支持克隆时抛出
     */
    @Override
    protected PageRequest clone() throws CloneNotSupportedException {
        PageRequest cloned = (PageRequest) super.clone();

        if (this.searches != null) {
            cloned.searches = new HashSet<>();
            for (PageSearch search : this.searches) {
                cloned.searches.add(search.clone());
            }
        }

        if (this.sorts != null) {
            cloned.sorts = new LinkedHashSet<>();
            for (PageSort sort : this.sorts) {
                cloned.sorts.add(sort.clone());
            }
        }

        return cloned;
    }

    /**
     * 返回搜索列表。
     * 如果列表为 null，则初始化列表。
     *
     * @return 搜索列表。
     */
    public Set<PageSearch> getSearches() {
        if (this.searches == null) {
            this.searches = new HashSet<>();
        }
        return this.searches;
    }

    /**
     * 返回排序列表。
     * 如果列表为 null，则初始化列表。
     *
     * @return 排序列表
     */
    public Set<PageSort> getSorts() {
        if (this.sorts == null) {
            this.sorts = new LinkedHashSet<>();
        }
        return this.sorts;
    }

    /**
     * 添加具有指定字段、操作和值的新搜索。
     *
     * @param field     要搜索的字段。
     * @param operation 要执行的操作。
     * @param value     搜索的值。
     */
    public void addSearch(String field, Operator operation, Object value) {
        addSearch(new PageSearch(field, operation, value));
    }

    /**
     * 将 Search 对象添加到搜索列表中。
     *
     * @param search 要添加的 PageSearch 对象。
     */
    public void addSearch(PageSearch search) {
        getSearches().add(search);
    }

    /**
     * 根据字段名称从列表中删除搜索。
     *
     * @param field 要搜索和删除的字段。
     */
    public void removeSearch(String field) {
        getSearches().removeIf(t -> field.equals(t.getField()));
    }

    /**
     * 根据字段名称从列表中删除搜索。
     *
     * @param search 要搜索和删除的 PageSearch 对象。
     */
    public void removeSearch(PageSearch search) {
        getSearches().remove(search);
    }

    /**
     * 检索与指定字段匹配的第一个 PageSearch 对象的值。
     *
     * @param field 要搜索的字段。
     * @return 匹配的 Search 对象的值，如果未找到，则为 null。
     */
    public Object getField(String field) {
        return getSearches().stream().filter(t -> field.equals(t.getField())).findFirst().map(t -> t.value).orElse(null);
    }

    /**
     * 检索与指定字段匹配的所有 PageSearch 对象的值。
     *
     * @param field 要搜索的字段。
     * @return 来自匹配 PageSearch 对象的值列表。
     */
    public List<Object> getFieldList(String field) {
        return getSearches().stream().filter(t -> field.equals(t.getField())).map(t -> t.value).collect(Collectors.toList());
    }

    /**
     * 检索与指定字段匹配的第一个 PageSearch 对象。
     *
     * @param field 要搜索的字段。
     * @return 匹配的 PageSearch 对象或 null（如果未找到）。
     */
    public PageSearch getSearch(String field) {
        return getSearches().stream().filter(t -> field.equals(t.getField())).findFirst().orElse(null);
    }

    /**
     * 检索与指定字段匹配的 PageSearch 对象的列表。
     *
     * @param field 要搜索的字段。
     * @return 匹配的 PageSearch 对象列表。如果没有，则返回空集合
     */
    public List<PageSearch> getSearchList(String field) {
        return getSearches().stream().filter(t -> field.equals(t.getField())).collect(Collectors.toList());
    }

    /**
     * 在与指定字段匹配的 Search 对象上执行提供的函数。
     *
     * @param field 要搜索的字段。
     * @param fun   要对匹配的 Search 对象执行的函数。
     */
    public void whenField(String field, Consumer<PageSearch> fun) {
        PageSearch search = getSearch(field);
        if (search != null) {
            fun.accept(search);
        }
    }

    /**
     * 在与指定字段匹配的 Search 对象列表上执行提供的函数。
     *
     * @param field 要搜索的字段。
     * @param fun   要对 Search 对象的匹配列表执行的函数。
     */
    public void whenFieldList(String field, Consumer<List<PageSearch>> fun) {
        List<PageSearch> searchList = getSearchList(field);
        if (!searchList.isEmpty()) {
            fun.accept(searchList);
        }
    }

    /**
     * 页面参数验证。
     *
     * @throws IllegalArgumentException 验证不成功时报错。
     */
    public void validate() {
        if (pageNum != null && pageNum < 1) {
            throw new IllegalArgumentException("页码不能小于1");
        }
        if (pageSize != null && (pageSize < 1 || pageSize > 1000)) {
            throw new IllegalArgumentException("页大小必须在1-1000之间");
        }
    }
}
