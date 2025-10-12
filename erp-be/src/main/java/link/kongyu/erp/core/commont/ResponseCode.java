package link.kongyu.erp.core.commont;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 标准响应代码及其描述的枚举。
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode {

    // Success
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),

    // Client errors
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    // Server errors
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEWAY(502, "Bad Gateway"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),

    // Custom errors
    SERVICE_EXCEPTION(50000, "Service Exception"),
    ;


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
        return null; // 或引发异常（如果可以的话）
    }

    /**
     * 获取响应代码。
     *
     * @return 响应代码。
     */
    @JsonView({ViewObject.class})
    public int getCode() {
        return code;
    }

    /**
     * 获取响应代码的描述。
     *
     * @return 响应代码的描述。
     */
    @JsonView({ViewObject.class})
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return code + " " + description;
    }
}
