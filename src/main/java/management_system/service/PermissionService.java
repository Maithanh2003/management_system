package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.domain.entity.Permission;
import management_system.domain.repository.PermissionRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.service.impl.IPermissionService;
import org.springframework.stereotype.Service;

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
}
