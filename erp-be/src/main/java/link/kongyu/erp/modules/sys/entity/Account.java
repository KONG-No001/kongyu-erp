package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.PersistingObject;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_account")
public class Account extends PersistingObject {

    @TableField("username")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String username;

    @TableField("password")
    private String password;

    @TableField("type")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Integer type;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;
}
