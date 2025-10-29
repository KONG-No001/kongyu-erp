package link.kongyu.erp.modules.sys.vo;

import link.kongyu.erp.modules.sys.constants.AccountType;
import lombok.Data;

@Data
public class AccountSimpleInfoDto {
    private Long id;
    private String username;
    private AccountType type;
    private Boolean enabled;
}
