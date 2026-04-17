package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.AccountPermissionAssociation;
import link.kongyu.erp.modules.sys.mapper.AccountPermissionAssociationMapper;
import link.kongyu.erp.modules.sys.service.AccountPermissionAssociationService;
import org.springframework.stereotype.Service;

@Service
public class AccountPermissionAssociationServiceImpl extends ServiceImpl<AccountPermissionAssociationMapper, AccountPermissionAssociation> implements AccountPermissionAssociationService {
}
