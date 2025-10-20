package link.kongyu.erp.core.page.metadata.enums;

import lombok.Getter;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/15
 */
public enum Operator {
    NONE(null),// 当为None时，需要自己构建语句
    EQ("="),
    NE("!="),
    LIKE("LIKE"),
    IN("in"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    BETWEEN("BETWEEN"),
    ;

    @Getter
    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
}
