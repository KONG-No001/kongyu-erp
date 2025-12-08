package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.PersistingObject;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.modules.sys.constants.PermissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_permission")
public class Permission extends PersistingObject {

    @TableField("business_group")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String businessGroup;

    @TableField("permission_name")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String permissionName;

    @TableField("type")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private PermissionType type;

    @TableField("access_resource")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String accessResource;


    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;
}
