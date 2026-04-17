package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Data
@TableName("sys_account")
public class Account {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonView(ViewObject.Basic.class)
    private Long id;

    @TableField("username")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String username;

    @TableField("password")
    private String password;

    @TableField("nickname")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String nickname;

    @TableField("real_name")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String realName;

    @TableField("phone")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String phone;

    @TableField("email")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String email;

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
