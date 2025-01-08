package management_system.service.impl;

import management_system.domain.dto.RoleDTO;
import management_system.domain.entity.Permission;
import management_system.domain.entity.Role;
import management_system.payload.RoleRequest;

import java.util.List;

public interface IRoleService {
    Role getRoleById (Long roleId);
    Role getRoleByCode(String code);
    Role getRoleByName (String roleName);
    List<Permission> getPermissionByRole(Long roleId);
    List<Role> getAllRole();
    void deleteRole(Long roleId);
    Role createRole(RoleRequest request);
    Role updateRole(Long roleId, RoleRequest request);
    RoleDTO convertToDto(Role role);

    Role convertToEntity(RoleDTO roleDto);
}
