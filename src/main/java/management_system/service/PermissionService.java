package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.dto.PermissionDTO;
import management_system.domain.entity.Permission;
import management_system.domain.repository.PermissionRepository;
import management_system.exception.AlreadyExistsException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.PermissionMapper;
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
    @Autowired
    private final PermissionMapper permissionMapper;

    @Override
    public List<PermissionDTO> getAllPermission() {
        return permissionMapper.toPermissionDTOs(permissionRepository.findAll());
    }

    @Override
    public PermissionDTO getPermissionById(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow( () -> new ResourceNotFoundException("not found permission by id" + permissionId)
                );

        return permissionMapper.toPermissionDTO(permission);
    }

    @Override
    public PermissionDTO getPermissionByName(String name) {
        Permission permission =  permissionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Permission not found with role: " + name));
        return permissionMapper.toPermissionDTO(permission);
    }

    @Override
    public PermissionDTO getPermissionByCode(String code) {
        Permission permission =  permissionRepository.findPermissionByCode(code)
                .orElseThrow(() -> new RuntimeException("Permission not found with role: " + code));
        return permissionMapper.toPermissionDTO(permission);
    }

    @Override
    public PermissionDTO createPermission(PermissionRequest request) {
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
        permissionRepository.save(permission);
        return permissionMapper.toPermissionDTO(permission);
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
