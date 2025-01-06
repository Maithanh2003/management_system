package management_system.service.impl;

import management_system.domain.entity.Permission;
import management_system.domain.entity.Role;
import management_system.payload.RoleRequest;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role getRoleById (Long roleId);
    Role getRoleByCode(String code);
    Role getRoleByName (String roleName);
    List<Permission> getPermissionByRole(Long roleId);
    List<Role> getAllRole();
    void deleteRole(Long roleId);
    Role createRole(RoleRequest request);
    Role updateRole(Long roleId, RoleRequest request);

}
