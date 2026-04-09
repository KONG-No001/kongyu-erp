package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.core.page.support.compiler.StaticMpQueryCompiler;
import link.kongyu.erp.modules.sys.entity.Role;
import link.kongyu.erp.modules.sys.service.RoleBaseService;
import link.kongyu.erp.modules.sys.service.RoleQueryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */

@Service
public class RoleQueryPageServiceImpl implements RoleQueryPageService {

    @Autowired
    RoleBaseService roleBaseService;

    @Override
    public PageResult<Role> findRolePage(PageRequest pageRequest) {
        pageRequest.validate();
        Page<Role> page = roleBaseService.page(PageUtils.toMpPage(pageRequest), StaticMpQueryCompiler.compile(pageRequest));
        return PageResult.getInstance(
                page.getRecords(),
                page.getTotal(),
                page.getCurrent(),
                page.getSize()
        );
    }
}
