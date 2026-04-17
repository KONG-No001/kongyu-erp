package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.RolePermissionAssociation;
import link.kongyu.erp.modules.sys.mapper.RolePermissionAssociationMapper;
import link.kongyu.erp.modules.sys.service.RolePermissionAssociationService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionAssociationServiceImpl extends ServiceImpl<RolePermissionAssociationMapper, RolePermissionAssociation> implements RolePermissionAssociationService {
}
