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
@TableName("sys_account_rule_association")
public class AccountRuleAssociation extends PersistingObject {

    @TableField("account_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long accountId;

    @TableField("role_id")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Long roleId;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;
}
