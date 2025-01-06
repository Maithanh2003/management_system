package management_system.service.impl;

import management_system.domain.entity.Permission;
import management_system.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    Role getRoleById (Long roleId);
    Role getRoleByCode(String code);
    Role getRoleByName (String roleName);
    List<Permission> getPermissionByRole(Long roleId);

}
