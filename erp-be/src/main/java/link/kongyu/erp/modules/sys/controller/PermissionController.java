package link.kongyu.erp.modules.sys.controller;

import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.core.batching.metadata.BatchingResult;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.entity.Permission;
import link.kongyu.erp.modules.sys.service.PermissionBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统权限控制层
 *
 * @author Luojun
 * @version v1.0.0
 * @since 2025/12/8
 */


@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBaseService permissionBaseService;

    @GetMapping("/info/{id}")
    public Result<?> getInfoById(@PathVariable long id) {
        return Result.success(permissionBaseService.getInfoById(id));
    }

    @RequestMapping("/find-page")
    public Result<PageResult<?>> findPage(@RequestBody PageRequest pageRequest) {
        return null;
    }

    @PostMapping("/add")
    public Result<?> addInfo(@RequestBody Permission permission) {
        permissionBaseService.addPermission(permission, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<?> update(@RequestBody Permission permission) {
        permissionBaseService.updatePermission(permission, SecurityContext.getUserId());
        return Result.success(null);
    }

    @DeleteMapping("/delete")
    public Result<BatchingResult> delete(long[] id) {
        return Result.success(permissionBaseService.batchDeletePermission(id, SecurityContext.getUserId()));
    }

    @PutMapping("/enable")
    public Result<BatchingResult> enable(long[] id, boolean enable) {
        return Result.success(permissionBaseService.batchEnablePermission(id,enable, SecurityContext.getUserId()));
    }

}
