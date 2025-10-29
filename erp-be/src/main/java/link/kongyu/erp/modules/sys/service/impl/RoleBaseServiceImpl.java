package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.Role;
import link.kongyu.erp.modules.sys.mapper.RoleMapper;
import link.kongyu.erp.modules.sys.service.RoleBaseService;
import org.springframework.stereotype.Service;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Service
public class RoleBaseServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleBaseService {
}
