package link.kongyu.erp.core.page.metadata.enums;

import lombok.Getter;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/15
 */
public enum Operator {
    EQ("="),
    NE("!="),
    LIKE("like"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<="),
    IN("in"),
    NONE(null); // 当为None时，需要自己构建语句

    @Getter
    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
}
