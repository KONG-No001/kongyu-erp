package link.kongyu.erp.common.constants;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import link.kongyu.erp.common.domain.PersistingObject;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */
public class PersistingFields {
    public static final String F_ID = "id";
    public static final String F_CREATED_DATE = "createdDate";
    public static final String F_CREATED_BY = "createdBy";
    public static final String F_UPDATED_DATE = "updatedDate";
    public static final String F_UPDATED_BY = "updatedBy";
    public static final String F_DELETE = "delete";

    public static final SFunction<PersistingObject, ?> SF_ID = PersistingObject::getId;
    public static final SFunction<PersistingObject, ?> SF_CREATED_DATE = PersistingObject::getId;
    public static final SFunction<PersistingObject, ?> SF_CREATED_BY = PersistingObject::getId;
    public static final SFunction<PersistingObject, ?> SF_UPDATED_DATE = PersistingObject::getId;
    public static final SFunction<PersistingObject, ?> SF_UPDATED_BY = PersistingObject::getId;
    public static final SFunction<PersistingObject, ?> SF_DELETE = PersistingObject::getId;


    protected PersistingFields() {
    }
}
