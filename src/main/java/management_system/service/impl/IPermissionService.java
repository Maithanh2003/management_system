package management_system.service.impl;

import management_system.domain.dto.PermissionDTO;
import management_system.payload.PermissionRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IPermissionService {
    List<PermissionDTO> getAllPermission();
    PermissionDTO getPermissionById(Long PermissionId);
    PermissionDTO getPermissionByName(String name);
    PermissionDTO getPermissionByCode(String code);
    PermissionDTO createPermission (PermissionRequest request);
    void deletePermissionById (Long permissionId);
    void deletePermissionByCode (String permissionCode);

}
