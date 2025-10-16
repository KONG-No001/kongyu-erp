package link.kongyu.erp.modules.sys.controller;


import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.common.domain.Result;
import link.kongyu.erp.common.domain.view.ViewObject;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageIService;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    AccountQueryPageIService accountQueryPageIService;

    @GetMapping("/info/{id}")
    public Result<AccountSimpleInfoDto> getSimpleInfoById(@PathVariable String id) {
        return Result.success(accountService.getSimpleInfoById(id));
    }

    @GetMapping("/list")
    @JsonView(ViewObject.List.class)
    public Result<List<Account>> list() {
        return Result.success(accountService.list());
    }

    @PostMapping("/page")
    @JsonView(ViewObject.List.class)
    public Result<PageResult<?>> page(@RequestBody PageRequest pageRequest) {
        return Result.success(accountQueryPageIService.findAccountPage(pageRequest));
    }

}
