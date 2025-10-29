package link.kongyu.erp.modules.sys.controller;


import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.infrastructure.security.SecurityContext;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
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

    @GetMapping("/info/{id}")
    public Result<AccountSimpleInfoDto> getSimpleInfoById(@PathVariable String id) {
        return Result.success(accountService.getSimpleInfoById(id));
    }

    @PostMapping("/add")
    public Result<?> addAccount(@RequestBody AccountSimpleInfoDto account) {
        accountService.addAccount(account, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/update")
    public Result<?> updateAccount(@RequestBody AccountSimpleInfoDto account) {
        accountService.updateAccount(account, SecurityContext.getUserId());
        return Result.success(null);
    }

    @PutMapping("/enable")
    public Result<?> batchEnableAccount(long[] ids, boolean enable) {
        return Result.success(accountService.batchEnableAccount(ids, enable, SecurityContext.getUserId()));
    }

    @PostMapping("/find-account-page")
    @JsonView({ViewObject.Detail.class})
    public Result<PageResult<?>> findAccountPage(@RequestBody PageRequest pageRequest) {
        return Result.success(accountQueryPageIService.findAccountPage(pageRequest));
    }

    @PostMapping("/find-simple-info-page")
    public Result<PageResult<?>> findSimpleAccountInfoPage(@RequestBody PageRequest pageRequest) {
        return Result.success(accountQueryPageIService.findAccountSimpleInfoPage(pageRequest));
    }

}
