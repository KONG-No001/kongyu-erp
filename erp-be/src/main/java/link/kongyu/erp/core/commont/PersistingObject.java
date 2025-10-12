package link.kongyu.erp.core.commont;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;

@Data
public class PersistingObject {

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    @JsonView({ViewObject.class})
    private String id;

    @TableField("created_date")
    private Date createdDate;

    @TableField("created_by")
    @JsonView({ViewObject.class})
    private String createdBy;

    @TableField("updated_date")
    @JsonView({ViewObject.class})
    private Date updatedDate;

    @TableField("updated_by")
    @JsonView({ViewObject.class})
    private String updatedBy;

}
