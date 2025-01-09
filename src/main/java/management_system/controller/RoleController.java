package management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.domain.dto.RoleDTO;
import management_system.domain.entity.Role;
import management_system.domain.entity.User;
import management_system.domain.repository.RoleRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateUserRequest;
import management_system.payload.RoleRequest;
import management_system.response.ApiResponse;
import management_system.service.RoleService;
import management_system.service.impl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final IRoleService roleService;

    @GetMapping("/{id}")
    public ApiResponse<RoleDTO> getRoleById(@PathVariable("id") Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role!= null) {
            log.info(role.getId().toString());
            return ApiResponse.<RoleDTO>builder()
                    .result(roleService.convertToDto(role))
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with ID: " + roleId);
        }
    }

    @GetMapping("/code/{code}")
    public ApiResponse<RoleDTO> getRoleByCode(@PathVariable("code") String code) {
        Role role = roleService.getRoleByCode(code);
        if (role != null) {
            log.info(role.getId().toString());
            return ApiResponse.<RoleDTO>builder()
                    .result(roleService.convertToDto(role))
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with code: " + code);
        }
    }

    @GetMapping("/name/{name}")
    public ApiResponse<RoleDTO> getRoleByName(@PathVariable("name") String name) {
        Role role = roleService.getRoleByName(name);
        if (role != null) {
            log.info(role.getId().toString());
            return ApiResponse.<RoleDTO>builder()
                    .result(roleService.convertToDto(role))
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with code: " + name);
        }
    }
    @GetMapping()
    public ApiResponse<List<RoleDTO>> getAllRole(){
        List<Role> roles = roleService.getAllRole();
        return ApiResponse.<List<RoleDTO>>builder()
                .message("danh sach quyen")
                .result(roles.stream().map(role -> roleService.convertToDto(role)).toList())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ApiResponse.<Void>builder()
                    .message("Role deleted successfully")
                    .code(204)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("error")
                    .code(404)
                    .build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<Role> createRole(@RequestBody RoleRequest request){
        Role role = roleService.createRole(request);
        return ApiResponse.<Role>builder()
                .message("da them role thanh cong")
                .result(role)
                .build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<RoleDTO> updateRole(@PathVariable Long id, @RequestBody RoleRequest request){
        Role role = roleService.updateRole(id, request);
        return ApiResponse.<RoleDTO>builder()
                .result(roleService.convertToDto(role))
                .message("update thanh xcong").build();
    }
}
