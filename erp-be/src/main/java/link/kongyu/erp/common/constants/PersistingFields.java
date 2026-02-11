package link.kongyu.erp.common.constants;

import link.kongyu.erp.modules.sys.constants.AbstractEntityFields;

import java.lang.reflect.Method;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/23
 */
public class PersistingFields extends AbstractEntityFields<PersistingFields> {
    public static final String FIELD_ID = "id";
    public static final String FIELD_CREATED_DATE = "createdDate";
    public static final String FIELD_CREATED_BY = "createdBy";
    public static final String FIELD_UPDATED_DATE = "updatedDate";
    public static final String FIELD_UPDATED_BY = "updatedBy";
    public static final String FIELD_DELETE = "deleted";

    protected PersistingFields() {
    }

    @Override
    protected Method getReadMethod(String name) {
        return null;
    }
}
