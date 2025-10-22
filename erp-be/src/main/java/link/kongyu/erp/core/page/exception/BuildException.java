package link.kongyu.erp.core.page.exception;

import link.kongyu.erp.common.domain.ResponseCode;
import link.kongyu.erp.common.exception.ServiceException;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/22
 */
public class BuildException extends ServiceException {
    public BuildException(String message) {
        super(message);
    }

    public BuildException(String message, Throwable cause) {
        super(ResponseCode.PARAM_VALID_ERROR.getCode(), message, cause);
    }
}
