package link.kongyu.erp.model.sys.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonView;
import link.kongyu.erp.core.commont.Result;
import link.kongyu.erp.model.sys.po.Account;
import link.kongyu.erp.model.sys.service.AccountIService;
import link.kongyu.erp.model.sys.vo.AccountSimpleInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    AccountIService accountService;

    @GetMapping("/info/{id}")
    @JsonView(AccountSimpleInfoVo.class)
    public Result<Account> getInfoById(@PathVariable String id) {
        return Result.success(accountService.getInfoById(id));
    }

    @GetMapping("/list")
    @JsonView(AccountSimpleInfoVo.class)
    public Result<List<Account>> list() {
        return Result.success(accountService.list());
    }

    @GetMapping("/page")
    public Result<Page<Account>> page(Page<Account> page) {
        return Result.success(accountService.page(page));
    }

}
