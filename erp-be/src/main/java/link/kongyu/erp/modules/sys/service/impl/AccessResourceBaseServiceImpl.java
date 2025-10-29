package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import link.kongyu.erp.modules.sys.entity.AccessResource;
import link.kongyu.erp.modules.sys.mapper.AccessResourceMapper;
import link.kongyu.erp.modules.sys.service.AccessResourceBaseService;
import org.springframework.stereotype.Service;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/29
 */

@Service
public class AccessResourceBaseServiceImpl extends ServiceImpl<AccessResourceMapper, AccessResource> implements AccessResourceBaseService {
}
