package link.kongyu.erp.core.page.metadata;

import link.kongyu.erp.core.page.metadata.enums.Direction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
public class PageSort implements Cloneable, Serializable {

    private static final long serialVersionUID = 2778693639116967959L;

    @Setter
    @Getter
    protected String field;

    @Setter
    @Getter
    protected Direction direction;

    public PageSort(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    /**
     * 深度复制当前对象
     *
     * @return 当前对象副本
     * @throws CloneNotSupportedException 不支持克隆时抛出
     */
    @Override
    protected PageSort clone() throws CloneNotSupportedException {
        return (PageSort) super.clone();
    }
}
