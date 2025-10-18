package link.kongyu.erp.common.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.view.ViewObject;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 持久化对象标准类。
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */

@Getter
@Setter
public class PersistingObject {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonView(ViewObject.Basic.class)
    private Long id;

    @TableField("created_date")
    @JsonView(ViewObject.Detail.class)
    private LocalDateTime createdDate;

    @TableField("created_by")
    @JsonView(ViewObject.Detail.class)
    private Long createdBy;

    @TableField("updated_date")
    @JsonView(ViewObject.Detail.class)
    private LocalDateTime updatedDate;

    @TableField("updated_by")
    @JsonView(ViewObject.Detail.class)
    private Long updatedBy;

    @TableLogic
    @TableField("deleted")
    @JsonIgnore
    private Boolean deleted = false;

    public void setCreateInfo(long createdBy, LocalDateTime createdDate) {
        this.createdBy = createdBy;
        this.createdDate = createdDate != null ? createdDate : LocalDateTime.now();
    }

    public void setUpdateInfo(long updateBy, LocalDateTime updatedDate) {
        this.updatedBy = updateBy;
        this.updatedDate = updatedDate != null ? updatedDate : LocalDateTime.now();
    }

}
