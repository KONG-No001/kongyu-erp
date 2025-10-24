package link.kongyu.erp.modules.sys.constants;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.constants.PersistingFields;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.kytils.map.MapUtils;

import java.util.Map;

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
    public static final Map<String, SFunction<Account, ?>> FIELD_MAPPINGS = MapUtils.getMapInstance(
            FIELD_ID, (SFunction<Account, ?>) Account::getId,
            FIELD_CREATED_DATE, (SFunction<Account, ?>) Account::getCreatedDate,
            FIELD_CREATED_BY, (SFunction<Account, ?>) Account::getCreatedBy,
            FIELD_UPDATED_DATE, (SFunction<Account, ?>) Account::getUpdatedDate,
            FIELD_UPDATED_BY, (SFunction<Account, ?>) Account::getUpdatedBy,
            FIELD_USERNAME, (SFunction<Account, ?>) Account::getUsername,
            FIELD_TYPE, (SFunction<Account, ?>) Account::getType,
            FIELD_ENABLED, (SFunction<Account, ?>) Account::getEnabled
    );

    private AccountFields() {
    }
}
