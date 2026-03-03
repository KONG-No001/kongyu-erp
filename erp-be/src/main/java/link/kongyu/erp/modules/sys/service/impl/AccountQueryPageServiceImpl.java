package link.kongyu.erp.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import link.kongyu.erp.core.page.metadata.PageRequest;
import link.kongyu.erp.core.page.support.PageResult;
import link.kongyu.erp.core.page.support.PageUtils;
import link.kongyu.erp.modules.sys.entity.Account;
import link.kongyu.erp.modules.sys.service.AccountBaseService;
import link.kongyu.erp.modules.sys.service.AccountQueryPageService;
import link.kongyu.erp.modules.sys.support.builder.AccountWrapperBuilder;
import link.kongyu.erp.modules.sys.vo.AccountSimpleInfoDto;
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
public class AccountQueryPageServiceImpl implements AccountQueryPageService {

    @Autowired
    private AccountWrapperBuilder wrapperBuilder;

    @Autowired
    private AccountBaseService accountBaseService;

    /**
     * 获取 Account 页面数据
     *
     * @param pageRequest 页面请求参数
     * @return Account 页面数据
     */
    @Override
    public PageResult<AccountSimpleInfoDto> findAccountPage(PageRequest pageRequest) {
        pageRequest.validate();
        Page<Account> page = accountBaseService.page(PageUtils.toMpPage(pageRequest), wrapperBuilder.buildQuery(accountBaseService.lambdaQuery(), pageRequest).getWrapper());
        return convertToSimpleDtoPage(page);
    }

    private AccountSimpleInfoDto convertToSimpleDto(Account account) {
        AccountSimpleInfoDto dto = new AccountSimpleInfoDto();
        BeanUtils.copyProperties(account, dto);
        return dto;
    }

    private PageResult<AccountSimpleInfoDto> convertToSimpleDtoPage(Page<Account> accountPage) {
        List<AccountSimpleInfoDto> dtos = accountPage
                .getRecords()
                .stream()
                .map(this::convertToSimpleDto)
                .collect(Collectors.toList());

        return PageResult.getInstance(
                dtos,
                accountPage.getTotal(),
                accountPage.getCurrent(),
                accountPage.getSize()
        );
    }

}
