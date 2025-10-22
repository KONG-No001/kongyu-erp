package link.kongyu.erp.common.domain;

/**
 * 标准响应代码及其描述的枚举。
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */
public enum ResponseCode {

    // 业务成功状态码
    SUCCESS(20000, "操作成功"),
    CREATED(20001, "创建成功"),

    // 业务错误状态码
    BUSINESS_ERROR(40000, "业务异常"),
    PARAM_VALID_ERROR(40001, "参数校验失败"),
    UNAUTHORIZED(40001, "未授权"),
    FORBIDDEN(40003, "禁止访问"),
    DATA_NOT_FOUND(40004, "数据不存在"),

    // 系统错误状态码
    SYSTEM_ERROR(50000, "系统异常"),
    SERVICE_UNAVAILABLE(50003, "服务不可用");;


    /**
     * 响应代码。
     */
    private final int code;

    /**
     * 响应代码的描述。
     */
    private final String description;

    /**
     * 使用给定的代码和描述构造 ResponseCode 枚举。
     *
     * @param code        响应代码。
     * @param description 响应代码的描述。
     */
    ResponseCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 返回与给定代码对应的 ResponseCode 枚举。
     *
     * @param code 要查找的响应代码。
     * @return 相应的 ResponseCode，如果代码无法识别，则为 null。
     */
    public static ResponseCode fromCode(int code) {
        for (ResponseCode responseCode : ResponseCode.values()) {
            if (responseCode.getCode() == code) {
                return responseCode;
            }
        }
        return SYSTEM_ERROR; // 或引发异常（如果可以的话）
    }

    /**
     * 获取响应代码。
     *
     * @return 响应代码。
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取响应代码的描述。
     *
     * @return 响应代码的描述。
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}
