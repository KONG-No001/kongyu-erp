package link.kongyu.erp.core.exception;


import link.kongyu.erp.core.commont.ResponseCode;

/**
 * 服务异常类
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */
public class ServiceException extends RuntimeException {

    private final ResponseCode code = ResponseCode.SERVICE_EXCEPTION;


    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseCode getCode() {
        return code;
    }
}
