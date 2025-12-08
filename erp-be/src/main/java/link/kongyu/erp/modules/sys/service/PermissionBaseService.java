package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.modules.sys.entity.Permission;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */
public interface PermissionBaseService extends IService<Permission> {
    Permission getInfoById(long id);

    void addPermission(Permission permission, long userId);

    void updatePermission(Permission permission, long userId);

    BatchingResult batchDeletePermission(long[] ids, long userId);

    BatchingResult batchEnablePermission(long[] ids, boolean enable, long userId);

    void enablePermission(long id, boolean enable, long userId);
}
