package link.kongyu.erp.model.sys.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.core.commont.PersistingObject;
import link.kongyu.erp.model.sys.vo.AccountSimpleInfoVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("`sys_account`")
public class Account extends PersistingObject {

    @TableField("username")
    @JsonView({AccountSimpleInfoVo.class})
    private String username;

    @TableField("password")
    private String password;

    @TableField("enabled")
    @JsonView({AccountSimpleInfoVo.class})
    private Boolean enabled;

}
