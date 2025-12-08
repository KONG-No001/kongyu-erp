package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.PersistingObject;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_permission_role_association")
public class PermissionRoleAssociation extends PersistingObject {

    @TableField("permission_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long permissionId;

    @TableField("role_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long roleId;

}
