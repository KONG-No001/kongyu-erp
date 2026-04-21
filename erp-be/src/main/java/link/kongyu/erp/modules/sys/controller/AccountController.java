package link.kongyu.erp.modules.sys.controller;


import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountPageService;
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
@RequestMapping("/sys/accounts")
public class AccountController {

    @Autowired
    AccountBaseService accountService;

    @Autowired
    AccountPageService accountQueryPageIService;

    @GetMapping("/{id}")
    @JsonView(ViewObject.Detail.class)
    public Result<Account> detail(@PathVariable Long id) {
        Account account = accountService.detail(id);
        account.setPassword(null);
        return Result.success(account);
    }

    @PostMapping({"", "/"})
    public Result<Account> create(@RequestBody Account account) {
        return Result.success(accountService.create(account, SecurityContext.getUserId()));
    }

    @PutMapping("/{id}")
    public Result<Account> update(@PathVariable Long id, @RequestBody Account account) {
        return Result.success(accountService.update(id, account, SecurityContext.getUserId()));
    }

    @PostMapping("/page")
    @JsonView(ViewObject.List.class)
    public Result<PageResult<?>> findAccountPage(@RequestBody PageRequest pageRequest) {
        return Result.success(accountQueryPageIService.getTableData(pageRequest));
    }

    @PutMapping("/batch-enable")
    public Result<?> batchEnableAccount(long[] ids, boolean enable) {
        return Result.success(accountService.batchEnable(ids, enable, SecurityContext.getUserId()));
    }

}
