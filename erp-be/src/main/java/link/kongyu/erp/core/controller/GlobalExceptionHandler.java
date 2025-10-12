package link.kongyu.erp.core.controller;

import link.kongyu.erp.core.commont.ResponseCode;
import link.kongyu.erp.core.commont.Result;
import link.kongyu.erp.core.exception.ServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局请求异常统一处理
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result<String> handleServiceException(ServiceException e) {
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.failure(ResponseCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
