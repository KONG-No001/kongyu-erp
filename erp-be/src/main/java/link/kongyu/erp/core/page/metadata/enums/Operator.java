package link.kongyu.erp.core.page.metadata.enums;

import lombok.Getter;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/15
 */


public enum Operator {
    NULL(null),// 当为NULL时，需要自己构建语句
    EQ("="),
    NE("!="),
    LIKE("LIKE"),
    IN("in"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    BETWEEN("BETWEEN"),
    IN_OR_EQ(null),
    IN_OR_LIKE(null),
    IN_OR_LEFT_LIKE(null),
    IN_OR_RIGHT_LIKE(null),
    ;

    @Getter
    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
}
