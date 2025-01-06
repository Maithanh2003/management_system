package management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.domain.entity.Role;
import management_system.domain.entity.User;
import management_system.exception.ResourceNotFoundException;
import management_system.response.ApiResponse;
import management_system.service.impl.IRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Slf4j
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/{id}")
    public ApiResponse<Role> getRoleById(@PathVariable("id") Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role!= null) {
            log.info(role.getId().toString());
            return ApiResponse.<Role>builder()
                    .result(role)
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with ID: " + roleId);
        }
    }

    @GetMapping("/code/{code}")
    public ApiResponse<Role> getRoleByCode(@PathVariable("code") String code) {
        Role role = roleService.getRoleByCode(code);
        if (role != null) {
            log.info(role.getId().toString());
            return ApiResponse.<Role>builder()
                    .result(role)
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with code: " + code);
        }
    }

    @GetMapping("/name/{name}")
    public ApiResponse<Role> getRoleByName(@PathVariable("name") String name) {
        Role role = roleService.getRoleByName(name);
        if (role != null) {
            log.info(role.getId().toString());
            return ApiResponse.<Role>builder()
                    .result(role)
                    .build();
        } else {
            throw new ResourceNotFoundException("Role not found with code: " + name);
        }
    }
}
