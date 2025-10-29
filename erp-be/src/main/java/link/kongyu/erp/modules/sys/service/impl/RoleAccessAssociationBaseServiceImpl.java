package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.RoleAccessAssociation;
import link.kongyu.erp.modules.sys.mapper.RoleAccessAssociationMapper;
import link.kongyu.erp.modules.sys.service.RoleAccessAssociationBaseService;
import org.springframework.stereotype.Service;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Service
public class RoleAccessAssociationBaseServiceImpl extends ServiceImpl<RoleAccessAssociationMapper, RoleAccessAssociation> implements RoleAccessAssociationBaseService {
}
