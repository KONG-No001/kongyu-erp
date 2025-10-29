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
@TableName("sys_role")
public class Role extends PersistingObject {

    @TableField("role_name")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String roleName;

    @TableField("parent_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long parentId;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;

}
