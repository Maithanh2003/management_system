package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.entity.Permission;
import management_system.domain.repository.PermissionRepository;
import management_system.exception.AlreadyExistsException;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.PermissionRequest;
import management_system.service.impl.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {
    @Autowired
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
        if (permissionRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Code already exists, please choose a different code.");
        }
        var permission = new Permission();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();

        permission.setName(name);
        permission.setCode(code);
        permission.setCreatedAt(LocalDate.now());
        permission.setCreatedBy(userPrincipal.getEmail());

        return permissionRepository.save(permission);
    }

    @Override
    public void deletePermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(
                () -> new ResourceNotFoundException("permission not found")
        );
        permission.setIsDeleted(1);
        permission.setUpdatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        permission.setUpdatedBy(userPrincipal.getEmail());
        permissionRepository.save(permission);

    }

    @Override
    public void deletePermissionByCode(String permissionCode) {
        Permission permission = permissionRepository.findPermissionByCode(permissionCode).orElseThrow(
                () -> new ResourceNotFoundException("permission not found")
        );
        permission.setIsDeleted(1);
        permission.setUpdatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        permission.setUpdatedBy(userPrincipal.getEmail());
        permissionRepository.save(permission);
    }
}
