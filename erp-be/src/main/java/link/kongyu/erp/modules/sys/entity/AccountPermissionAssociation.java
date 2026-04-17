package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.modules.sys.constants.EffectType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/4/17
 */

@Data
@TableName("sys_account_permission_association")
public class AccountPermissionAssociation {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonView(ViewObject.Basic.class)
    private Long id;

    @TableField("account_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long accountId;

    @TableField("permission_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long permissionId;

    @TableField("effect_type")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private EffectType effectType;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;

    @TableField("remark")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String remark;

    @TableField("created_by")
    @JsonView(ViewObject.Detail.class)
    private Long createdBy;

    @TableField("created_time")
    @JsonView(ViewObject.Detail.class)
    private LocalDateTime createdTime;

    @TableField("updated_by")
    @JsonView(ViewObject.Detail.class)
    private Long updatedBy;

    @TableField("updated_time")
    @JsonView(ViewObject.Detail.class)
    private LocalDateTime updatedTime;
}
