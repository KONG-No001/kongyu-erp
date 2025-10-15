package link.kongyu.erp.common.exception;


import link.kongyu.erp.common.domain.ResponseCode;
import lombok.Getter;

/**
 * 服务异常类
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */
public class ServiceException extends RuntimeException {

    @Getter
    private final int code;

    public ServiceException(String message) {
        this(ResponseCode.BUSINESS_ERROR.getCode(), message);
    }

    public ServiceException(ResponseCode code) {
        this(code.getCode(), code.getDescription());
    }

    public ServiceException(ResponseCode code, String message) {
        this(code.getCode(), message);
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
