package link.kongyu.erp.modules.sys.constants.fields;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.constants.PersistingFields;
import link.kongyu.erp.modules.sys.entity.Account;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */
public class AccountFields extends PersistingFields {
    public static final String F_USERNAME = "username";
    public static final String F_PASSWORD = "password";
    public static final String F_TYPE = "type";
    public static final String F_ENABLED = "enabled";

    public static final SFunction<Account, ?> SF_ID = Account::getId;
    public static final SFunction<Account, ?> SF_CREATED_DATE = Account::getId;
    public static final SFunction<Account, ?> SF_CREATED_BY = Account::getId;
    public static final SFunction<Account, ?> SF_UPDATED_DATE = Account::getId;
    public static final SFunction<Account, ?> SF_UPDATED_BY = Account::getId;
    public static final SFunction<Account, ?> SF_DELETE = Account::getId;
    public static final SFunction<Account, ?> SF_USERNAME = Account::getUsername;
    public static final SFunction<Account, ?> SF_PASSWORD = Account::getPassword;
    public static final SFunction<Account, ?> SF_TYPE = Account::getType;
    public static final SFunction<Account, ?> SF_ENABLED = Account::getEnabled;

    protected AccountFields() {
    }
}
