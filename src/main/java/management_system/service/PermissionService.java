package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.domain.entity.Permission;
import management_system.domain.repository.PermissionRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.PermissionRequest;
import management_system.service.impl.IPermissionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    private final PermissionRepository permissionRepository;

    @Override
    public List<Permission> getAllPermission() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission getPermissionById(Long permissionId) {
        return permissionRepository.findById(permissionId)
                .orElseThrow( () -> new ResourceNotFoundException("not found permission by id" + permissionId)
        );
    }

    @Override
    public Permission getPermissionByName(String name) {
        return permissionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Permission not found with role: " + name));
    }

    @Override
    public Permission getPermissionByCode(String code) {
        return permissionRepository.findPermissionByCode(code)
                .orElseThrow(() -> new RuntimeException("Permission not found with role: " + code));
    }

    @Override
    public Permission createPermission(PermissionRequest request) {
        String name = request.getName();
        String code = request.getCode();
        var permission = new Permission();

        permission.setName(name);
        permission.setCode(code);
        permission.setCreated_at(LocalDate.now());
        permission.setCreated_by("system");

        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new ResourceNotFoundException("permission not found")
        );
        permission.setIs_deleted(1);
        permission.setUpdated_at(LocalDate.now());
        permission.setUpdated_by("System");
        permissionRepository.save(permission);

    }

    @Override
    public void deletePermissionByCode(String permissionCode) {
        Permission permission = permissionRepository.findPermissionByCode(permissionCode).orElseThrow(
                () -> new ResourceNotFoundException("permission not found")
        );
        permission.setIs_deleted(1);
        permission.setUpdated_at(LocalDate.now());
        permission.setUpdated_by("System");
        permissionRepository.save(permission);
    }
}
