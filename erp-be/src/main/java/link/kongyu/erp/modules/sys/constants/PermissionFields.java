package link.kongyu.erp.modules.sys.constants;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.constants.PersistingFields;
import link.kongyu.erp.common.utils.MapUtils;
import link.kongyu.erp.modules.sys.entity.Permission;

import java.util.Map;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/12/8
 */
public class PermissionFields extends PersistingFields {
    public static final String FIELD_PERMISSION_NAME = "permissionName";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_ACCESS_RESOURCE = "accessResource";
    public static final String FIELD_BUSINESS_GROUP = "businessGroup";
    public static final String FIELD_ENABLED = "enabled";


    /**
     * 一般字段查询映射
     */
    public static final Map<String, SFunction<Permission, ?>> FIELD_MAPPINGS = MapUtils.getMapInstance(
            FIELD_ID, (SFunction<Permission, ?>) Permission::getId,
            FIELD_CREATED_DATE, (SFunction<Permission, ?>) Permission::getCreatedDate,
            FIELD_CREATED_BY, (SFunction<Permission, ?>) Permission::getCreatedBy,
            FIELD_UPDATED_DATE, (SFunction<Permission, ?>) Permission::getUpdatedDate,
            FIELD_UPDATED_BY, (SFunction<Permission, ?>) Permission::getUpdatedBy,
            FIELD_DELETE, (SFunction<Permission, ?>) Permission::getUpdatedBy,
            FIELD_PERMISSION_NAME, (SFunction<Permission, ?>) Permission::getPermissionName,
            FIELD_TYPE, (SFunction<Permission, ?>) Permission::getType,
            FIELD_ACCESS_RESOURCE, (SFunction<Permission, ?>) Permission::getAccessResource,
            FIELD_BUSINESS_GROUP, (SFunction<Permission, ?>) Permission::getBusinessGroup,
            FIELD_ENABLED, (SFunction<Permission, ?>) Permission::getEnabled
    );

    private PermissionFields() {
    }
}
