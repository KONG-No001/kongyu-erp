package link.kongyu.erp.modules.sys.constants;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.kytils.map.MapUtils;

import java.util.Map;

import static link.kongyu.erp.modules.sys.constants.AccountFieldConstants.*;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/22
 */
public class AccountFieldMappings {
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

    private AccountFieldMappings() {
    }
}
