package link.kongyu.erp.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import link.kongyu.erp.modules.sys.entity.RoleAccountAssociation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAccountAssociationMapper extends BaseMapper<RoleAccountAssociation> {

    @Select("SELECT id, created_date, created_by, updated_date, updated_by, deleted, account_id, role_id, enabled " +
            "FROM sys_role_account_association WHERE role_id = #{roleId} AND account_id = #{accountId} LIMIT 1")
    RoleAccountAssociation selectIncludingDeleted(@Param("roleId") Long roleId, @Param("accountId") Long accountId);
}
