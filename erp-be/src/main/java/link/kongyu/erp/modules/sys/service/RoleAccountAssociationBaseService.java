package link.kongyu.erp.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import link.kongyu.erp.modules.sys.entity.RoleAccountAssociation;

public interface RoleAccountAssociationBaseService extends IService<RoleAccountAssociation> {

    RoleAccountAssociation bind(Long roleId, Long accountId, Long userId);

    void unbind(Long roleId, Long accountId, Long userId);
}
