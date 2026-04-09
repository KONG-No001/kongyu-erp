package link.kongyu.erp.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import link.kongyu.erp.modules.sys.entity.PermissionRoleAssociation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRoleAssociationMapper extends BaseMapper<PermissionRoleAssociation> {

    @Select("SELECT id, created_by, created_date, updated_by, updated_date, deleted, role_id, permission_id " +
            "FROM sys_permission_role_association WHERE role_id = #{roleId} AND permission_id = #{permissionId} LIMIT 1")
    PermissionRoleAssociation selectIncludingDeleted(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
