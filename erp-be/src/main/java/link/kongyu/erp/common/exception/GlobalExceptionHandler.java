package link.kongyu.erp.common.exception;

import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Result<String> handleServiceException(ServiceException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.failure(ResponseCode.fromCode(e.getCode()), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("参数校验失败：{}", message);
        return Result.failure(ResponseCode.PARAM_VALID_ERROR, message);
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常：", e);
        return Result.failure(ResponseCode.SYSTEM_ERROR, "系统异常，请稍后重试");
    }

}
