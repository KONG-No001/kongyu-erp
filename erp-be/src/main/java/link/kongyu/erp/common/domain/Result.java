package link.kongyu.erp.common.domain;

import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Getter;

/**
 * 表示操作结果的类。包含成功状态、代码和结果数据。
 *
 * @param <T> 结果数据类型。
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/27
 */
public class Result<T> {

    /**
     * 是否成功。
     */
    @Getter
    @JsonView({ViewObject.class})
    protected boolean success;

    /**
     * 响应代码。
     */
    @Getter
    @JsonView({ViewObject.class})
    protected int code;

    /**
     * 传递消息。
     */
    @Getter
    @JsonView({ViewObject.class})
    protected String message;

    /**
     * 操作成功的实际结果数据。
     */
    @Getter
    @JsonView({ViewObject.class})
    protected T data;

    /**
     * 响应时间戳
     */
    @Getter
    @JsonView({ViewObject.class})
    protected Long timestamp;

    /**
     * Success 静态工厂。
     *
     * @param <T>  结果数据类型。
     * @param data 结果数据。
     * @return 新 Success Result 对象。
     */
    public static <T> Result<T> success(T data) {
        return success(ResponseCode.SUCCESS, data);
    }

    /**
     * Success 静态工厂。
     *
     * @param <T>  结果数据类型。
     * @param code 响应代码。
     * @param data 结果数据。
     * @return 新 Success Result 对象。
     */
    public static <T> Result<T> success(ResponseCode code, T data) {
        return success(code.getCode(), code.getDescription(), data);
    }

    /**
     * Success 静态工厂。
     *
     * @param <T>     结果数据类型。
     * @param code    响应代码。
     * @param message 响应信息。
     * @param data    结果数据。
     * @return 新 Success Result 对象。
     */
    public static <T> Result<T> success(int code, String message, T data) {
        Result<T> res = new Result<>();
        res.success = true;
        res.code = code;
        res.message = message;
        res.data = data;
        res.timestamp = System.currentTimeMillis();
        return res;
    }

    /**
     * Failure 静态工厂。
     *
     * @param code 响应代码。
     * @param <T>  失败统一返回 T
     * @return 新 Failure Result 对象。
     */
    public static <T> Result<T> failure(ResponseCode code, String message) {
        return failure(code.getCode(), message != null ? message : code.getDescription());
    }

    /**
     * Failure 静态工厂。
     *
     * @param code    响应代码。
     * @param message 响应信息。
     * @param <T>     失败统一返回 T
     * @return 新 Failure Result 对象。
     */
    public static <T> Result<T> failure(int code, String message) {
        Result<T> res = new Result<>();
        res.success = false;
        res.code = code;
        res.message = message;
        res.timestamp = System.currentTimeMillis();
        return res;
    }

}
