package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.modules.sys.entity.PermissionRoleAssociation;

public interface PermissionRoleAssociationBaseService extends IService<PermissionRoleAssociation> {

    PermissionRoleAssociation bind(Long permissionId, Long roleId, Long userId);

    void unbind(Long permissionId, Long roleId, Long userId);
}
