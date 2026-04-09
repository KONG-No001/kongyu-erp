package link.kongyu.erp.modules.sys.constants.fields;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.constants.PersistingFields;
import link.kongyu.erp.modules.sys.entity.Role;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */
public class RoleFields extends PersistingFields {
    public static final String F_ROLE_NAME = "roleName";
    public static final String F_PARENT_ID = "parentId";
    public static final String F_ENABLED = "enabled";

    public static final SFunction<Role, ?> SF_ID = Role::getId;
    public static final SFunction<Role, ?> SF_CREATED_DATE = Role::getId;
    public static final SFunction<Role, ?> SF_CREATED_BY = Role::getId;
    public static final SFunction<Role, ?> SF_UPDATED_DATE = Role::getId;
    public static final SFunction<Role, ?> SF_UPDATED_BY = Role::getId;
    public static final SFunction<Role, ?> SF_DELETE = Role::getId;
    public static final SFunction<Role, ?> SF_ROLE_NAME = Role::getRoleName;
    public static final SFunction<Role, ?> SF_PARENT_ID = Role::getParentId;
    public static final SFunction<Role, ?> SF_ENABLED = Role::getEnabled;


    private RoleFields() {
    }
}
