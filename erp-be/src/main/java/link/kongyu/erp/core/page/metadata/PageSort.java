package link.kongyu.erp.core.page.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class PageSort {

    @Setter
    @Getter
    protected String field;

    @Setter
    @Getter
    protected String direction;
}
