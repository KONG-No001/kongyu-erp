package link.kongyu.erp.modules.sys.constants;

import link.kongyu.erp.core.page.support.builder.FieldMapping;
import link.kongyu.erp.common.constants.PersistingFields;
import link.kongyu.erp.modules.sys.entity.Account;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/22
 */
public class AccountFields extends PersistingFields {
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_ENABLED = "enabled";

    /**
     * 一般字段查询映射
     */
    public static final FieldMapping<Account> FIELD_MAPPINGS = new FieldMapping<>(Account.class);

    private AccountFields() {
    }
}
