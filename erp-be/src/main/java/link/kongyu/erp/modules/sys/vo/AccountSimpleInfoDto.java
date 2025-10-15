package link.kongyu.erp.modules.sys.vo;

import lombok.Data;

@Data
public class AccountSimpleInfoDto {
    private Long id;
    private String username;
    private Boolean enabled;
}
