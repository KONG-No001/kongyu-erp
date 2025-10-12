package link.kongyu.erp.core.commont;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 表示操作结果的类。包含成功状态、代码和结果数据。
 *
 * @param <T> 结果数据类型。
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/27
 */
public class Result<T> {

    public static final Result<Void> SUCCESS_RESULT = success(null);

    /**
     * 指示操作是否成功。
     */
    protected boolean success;

    /**
     * 与结果关联的状态代码。
     */
    protected ResponseCode code;

    /**
     * 操作的实际结果数据。
     */
    protected T result;

    /**
     * 创建 Success Result 对象的静态工厂。
     *
     * @param <T>    结果数据类型。
     * @param result 结果数据。
     * @return 新 Success Result 对象。
     */
    public static <T> Result<T> success(T result) {
        Result<T> res = new Result<>();
        res.setSuccess(true);
        res.setCode(ResponseCode.OK);
        res.setResult(result);
        return res;
    }

    /**
     * 用于 failure Result 对象的静态工厂。
     *
     * @param <T>  结果数据类型。
     * @param code 错误代码。
     * @return 新 Failure Result 对象。
     */
    public static <T> Result<T> failure(ResponseCode code) {
        Result<T> res = new Result<>();
        res.setSuccess(false);
        res.setCode(code);
        return res;
    }

    public static Result<String> failure(ResponseCode code, String message) {
        Result<String> res = new Result<>();
        res.setSuccess(false);
        res.setCode(code);
        res.setResult(message);
        return res;
    }

    /**
     * 获取操作的成功状态。
     *
     * @return 如果操作成功，则为 true，否则为 false。
     */
    @JsonView({ViewObject.class})
    public boolean isSuccess() {
        return success;
    }

    /**
     * 设置操作的成功状态。
     *
     * @param success 如果操作成功，则为 true，否则为 false。
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取与结果关联的状态代码。
     *
     * @return 状态代码。
     */
    @JsonView({ViewObject.class})
    public ResponseCode getCode() {
        return code;
    }

    /**
     * 设置与结果关联的状态代码。
     *
     * @param code 要设置的状态代码。
     */
    public void setCode(ResponseCode code) {
        this.code = code;
    }

    /**
     * 获取操作的实际结果数据。
     *
     * @return 结果数据。
     */
    @JsonView({ViewObject.class})
    public T getResult() {
        return result;
    }

    /**
     * 设置操作的实际结果数据。
     *
     * @param result 要设置的结果数据。
     */
    public void setResult(T result) {
        this.result = result;
    }

}
