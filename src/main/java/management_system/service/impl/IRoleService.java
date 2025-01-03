package management_system.service.impl;

import management_system.domain.entity.Role;

import java.util.Optional;

public interface IRoleService {
    Role getRoleById (Long roleId);
    Role getRoleByCode(String code);
    Role getRoleByName (String roleName);

}
