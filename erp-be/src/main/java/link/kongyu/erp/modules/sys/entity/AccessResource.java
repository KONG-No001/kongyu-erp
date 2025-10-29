package link.kongyu.erp.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.PersistingObject;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.modules.sys.constants.AccessResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_access_resource")
public class AccessResource extends PersistingObject {

    @TableField("type")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private AccessResourceType type;

    @TableField("resource")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private String resource;

    @TableField("enabled")
    @JsonView({ViewObject.Detail.class, ViewObject.List.class})
    private Boolean enabled;
}
