package link.kongyu.erp.modules.sys.controller;

import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.entity.Role;
import link.kongyu.erp.modules.sys.service.RoleBaseService;
import link.kongyu.erp.modules.sys.service.RoleQueryPageService;
import link.kongyu.erp.modules.sys.support.compiler.RoleQueryCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luojun
 * @version v1.0.0
 * @since 2026/3/4
 */


@RestController
@RequestMapping("/role")
public class RolePageController {

    @Autowired
    RoleQueryPageService roleQueryPageService;

    @Autowired
    RoleBaseService roleBaseService;

    @Autowired
    RoleQueryCompiler roleQueryCompiler;

    @PostMapping({"", "/"})
    public Result<Role> create(@RequestBody Role role) {
        return Result.success(roleBaseService.create(role, SecurityContext.getUserId()));
    }

    @PutMapping("/{id}")
    public Result<Role> update(@PathVariable Long id, @RequestBody Role role) {
        return Result.success(roleBaseService.update(id, role, SecurityContext.getUserId()));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        roleBaseService.delete(id, SecurityContext.getUserId());
        return Result.success(null);
    }

    @GetMapping("/{id}")
    @JsonView(ViewObject.Detail.class)
    public Result<Role> detail(@PathVariable Long id) {
        return Result.success(roleBaseService.detail(id));
    }

    @PostMapping("/search")
    @JsonView(ViewObject.List.class)
    public Result<PageResult<Role>> search(@RequestBody PageRequest pageRequest) {
        return Result.success(roleBaseService.search(pageRequest, roleQueryCompiler.compile(pageRequest)));
    }

    @PostMapping("/find-role-page")
    public Result<PageResult<?>> findRolePage(@RequestBody PageRequest pageRequest) {
        return Result.success(roleQueryPageService.findRolePage(pageRequest));
    }

    @PostMapping("/add-role")
    public Result<?> addRole(@RequestBody Role role) {
        roleBaseService.addRole(role, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/update-role")
    public Result<?> updateRole(@RequestBody Role role) {
        roleBaseService.updateRole(role, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/enable-role")
    public Result<?> enableRole(long id, boolean enable) {
        roleBaseService.enableRole(id, enable, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/batch-enable-role")
    public Result<?> batchEnableRole(long[] ids, boolean enable) {
        return Result.success(roleBaseService.batchEnableRole(ids, enable, SecurityContext.getUserId()));
    }
}
