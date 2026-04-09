package link.kongyu.erp.modules.sys.controller;

import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.entity.Permission;
import link.kongyu.erp.modules.sys.service.PermissionBaseService;
import link.kongyu.erp.modules.sys.support.compiler.PermissionQueryCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionBaseService permissionBaseService;

    @Autowired
    private PermissionQueryCompiler permissionQueryCompiler;

    @PostMapping({"", "/"})
    public Result<Permission> create(@RequestBody Permission permission) {
        return Result.success(permissionBaseService.create(permission, SecurityContext.getUserId()));
    }

    @PutMapping("/{id}")
    public Result<Permission> update(@PathVariable Long id, @RequestBody Permission permission) {
        return Result.success(permissionBaseService.update(id, permission, SecurityContext.getUserId()));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        permissionBaseService.delete(id, SecurityContext.getUserId());
        return Result.success(null);
    }

    @GetMapping("/{id}")
    @JsonView(ViewObject.Detail.class)
    public Result<Permission> detail(@PathVariable Long id) {
        return Result.success(permissionBaseService.detail(id));
    }

    @PostMapping("/search")
    @JsonView(ViewObject.List.class)
    public Result<PageResult<Permission>> search(@RequestBody PageRequest pageRequest) {
        return Result.success(permissionBaseService.search(pageRequest, permissionQueryCompiler.compile(pageRequest)));
    }
}
