package management_system.service;

import lombok.RequiredArgsConstructor;
import management_system.domain.dto.RoleDTO;
import management_system.domain.entity.Permission;
import management_system.domain.entity.Role;
import management_system.domain.repository.PermissionRepository;
import management_system.domain.repository.RoleRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.RoleRequest;
import management_system.service.impl.IRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PermissionRepository permissionRepository;
    @Autowired
    private final ModelMapper modelMapper;


    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with code: " + roleId));
    }

    @Override
    public Role getRoleByCode(String code) {
        return roleRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with code: " + code));
    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
    }

    @Override
    public List<Permission> getPermissionByRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with code: " + roleId));
        return role.getPermission().stream().toList();

    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role =  roleRepository.findById(roleId).orElseThrow(
                ()-> new ResourceNotFoundException("role not found")
        );
        role.markAsDeleted();
        roleRepository.save(role);
    }

    @Override
    public Role createRole(RoleRequest request) {
        var role = new Role();
        role.setName(request.getName());
        role.setCode(request.getCode());
        var permissions = request.getPermission().stream()
                .map(permissionRepository::findPermissionByCode)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
        role.setCreatedAt(LocalDate.now());
        role.setCreatedBy("System");
        role.setPermission(permissions);

        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long roleId, RoleRequest request) {
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new ResourceNotFoundException("ROLE NOT FOUND")
        );
        var additionalPermissions = request.getPermission().stream()
                .map(permissionRepository::findPermissionByCode)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());

        role.getPermission().addAll(additionalPermissions);
        role.setName(request.getName());
        role.setCode(request.getCode());
        role.setUpdatedAt(LocalDate.now());
        role.setUpdatedBy("System");
        return roleRepository.save(role);

    }
    @Override
    public RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
    @Override
    public Role convertToEntity(RoleDTO roleDto) {
        return modelMapper.map(roleDto, Role.class);
    }
}
