package link.kongyu.erp.core.page.support;

import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 表示页面记录的类。
 *
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 48926960615834777L;

    /**
     * 页面记录
     */
    @Setter
    @JsonView({ViewObject.class})
    private List<T> records;

    /**
     * 总计数
     */
    @Getter
    @Setter
    @JsonView({ViewObject.class})
    private long totalCount;

    /**
     * 页码
     */
    @Getter
    @Setter
    @JsonView({ViewObject.class})
    private long pageNum;

    /**
     * 页大小
     */
    @Getter
    @Setter
    @JsonView({ViewObject.class})
    private long pageSize;

    /**
     * 总页数
     */
    @Getter
    @Setter
    @JsonView({ViewObject.class})
    private long pages;


    /**
     * 静态工厂
     *
     * @param records    页面记录
     * @param totalCount 总计数
     * @param pageNum    页码
     * @param pageSize   页大小
     * @param <T>        结果数据类型。
     * @return 页面记录对象
     */
    public static <T> PageResult<T> getInstance(List<T> records, long totalCount, long pageNum, long pageSize) {
        PageResult<T> result = new PageResult<>();
        result.records = records;
        result.totalCount = totalCount;
        result.pageNum = pageNum;
        result.pageSize = pageSize;
        result.pages = (int) Math.ceil((double) totalCount / pageSize);
        return result;
    }

}
