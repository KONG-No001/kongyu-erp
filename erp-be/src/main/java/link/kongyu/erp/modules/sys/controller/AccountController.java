package link.kongyu.erp.modules.sys.controller;


import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageService;
import link.kongyu.erp.modules.sys.support.compiler.AccountQueryCompiler;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户账号控制层。
 *
 * @author KONG_YU
 * @version v1.0.0
 * @since 2024/9/28
 */

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountBaseService accountService;

    @Autowired
    AccountQueryPageService accountQueryPageIService;

    @Autowired
    AccountQueryCompiler accountQueryCompiler;

    @PostMapping({"", "/"})
    public Result<Account> create(@RequestBody Account account) {
        return Result.success(accountService.create(account, SecurityContext.getUserId()));
    }

    @PutMapping("/{id}")
    public Result<Account> update(@PathVariable Long id, @RequestBody Account account) {
        return Result.success(accountService.update(id, account, SecurityContext.getUserId()));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        accountService.delete(id, SecurityContext.getUserId());
        return Result.success(null);
    }

    @GetMapping("/{id}")
    @JsonView(ViewObject.Detail.class)
    public Result<Account> detail(@PathVariable Long id) {
        return Result.success(accountService.detail(id));
    }

    @PostMapping("/search")
    @JsonView(ViewObject.List.class)
    public Result<PageResult<Account>> search(@RequestBody PageRequest pageRequest) {
        return Result.success(accountService.search(pageRequest, accountQueryCompiler.compile(pageRequest)));
    }

    @GetMapping("/info/{id}")
    public Result<AccountSimpleInfoView> getSimpleInfoById(@PathVariable String id) {
        return Result.success(accountService.getSimpleInfoById(id));
    }

    @PostMapping("/add")
    public Result<?> addAccount(@RequestBody Account account) {
        accountService.addAccount(account, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<?> updateAccount(@RequestBody Account account) {
        accountService.updateAccount(account, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/batch-enable")
    public Result<?> batchEnableAccount(long[] ids, boolean enable) {
        return Result.success(accountService.batchEnableAccount(ids, enable, SecurityContext.getUserId()));
    }

    @PostMapping("/find-account-page")
    public Result<PageResult<?>> findAccountPage(@RequestBody PageRequest pageRequest) {
        return Result.success(accountQueryPageIService.findAccountPage(pageRequest));
    }

}
