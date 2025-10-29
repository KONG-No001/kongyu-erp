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
@TableName("sys_role_access_association")
public class RoleAccessAssociation extends PersistingObject {

    @TableField("role_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long roleId;

    @TableField("access_resource_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long accessResourceId;

}
