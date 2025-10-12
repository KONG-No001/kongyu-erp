package link.kongyu.erp.core.page.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 页面查询字段
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/27
 */

@EqualsAndHashCode
public class PageSearch implements Cloneable, Serializable {

    private static final long serialVersionUID = 7763614972873946025L;

    @Getter
    @Setter
    protected String field;

    @Getter
    @Setter
    protected String operation;

    @Getter
    @Setter
    protected Object value;

    public PageSearch(String field, Object value) {
        this.field = field;
        this.value = value;
    }

    public PageSearch(String field, String operation, Object value) {
        this.field = field;
        this.operation = operation;
        this.value = value;
    }

    /**
     * 深度复制当前对象
     *
     * @return 当前对象副本
     * @throws CloneNotSupportedException 不支持克隆时抛出
     */
    @Override
    protected PageSearch clone() throws CloneNotSupportedException {
        return (PageSearch) super.clone();
    }
}
