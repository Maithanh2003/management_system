package management_system.service.impl;

import management_system.domain.entity.Permission;
import management_system.payload.PermissionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPermissionService {
    List<Permission> getAllPermission();
    Permission getPermissionById(Long PermissionId);
    Permission getPermissionByName(String name);
    Permission getPermissionByCode(String code);
    Permission createPermission (PermissionRequest request);
    void deletePermissionById (Long permissionId);
    void deletePermissionByCode (String permissionCode);

}
