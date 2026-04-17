package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountPageService;
import link.kongyu.erp.modules.sys.support.compiler.AccountQueryCompiler;
import link.kongyu.erp.modules.sys.vo.AccountTableView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Account页面查询服务
 *
 * @author Luojun
 * @version v1.0.0
 * @since 2025/10/16
 */
@Service
public class AccountPageServiceImpl implements AccountPageService {

    @Autowired
    private AccountBaseService accountBaseService;

    @Autowired
    private AccountQueryCompiler accountQueryCompiler;

    @Override
    public PageResult<AccountTableView> getTableData(PageRequest pageRequest) {
        pageRequest.validate();
        Page<Account> page = accountBaseService.page(PageUtils.toMpPage(pageRequest), accountQueryCompiler.compile(pageRequest));
        return convertToSimpleDtoPage(page);
    }

    private AccountTableView convertToSimpleDto(Account account) {
        AccountTableView dto = new AccountTableView();
        BeanUtils.copyProperties(account, dto);
        return dto;
    }

    private PageResult<AccountTableView> convertToSimpleDtoPage(Page<Account> accountPage) {
        List<AccountTableView> views = accountPage
                .getRecords()
                .stream()
                .map(this::convertToSimpleDto)
                .collect(Collectors.toList());

        return PageResult.getInstance(
                views,
                accountPage.getTotal(),
                accountPage.getCurrent(),
                accountPage.getSize()
        );
    }

}
