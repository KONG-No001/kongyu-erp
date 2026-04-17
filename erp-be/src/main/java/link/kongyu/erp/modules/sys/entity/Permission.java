package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.modules.sys.constants.PermissionType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Data
@TableName("sys_permission")
public class Permission {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonView(ViewObject.Basic.class)
    private Long id;

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

    @TableField("parent_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long parentId;

    @TableField("sort_no")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Integer sortNo;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;

    @TableField("deleted")
    @TableLogic
    @JsonView(ViewObject.Detail.class)
    private Boolean deleted;

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
