package link.kongyu.erp.core.page.support;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.kongyu.erp.core.page.metadata.PageRequest;

import java.util.List;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */
public class PageUtils {

    public static <T> Page<T> toMpPage(PageRequest request) {
        return new Page<>(request.getPageNum(), request.getPageSize());
    }

    public static <T> PageResult<T> toPageResult(Page<T> page) {
        return PageResult.getInstance(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

}
