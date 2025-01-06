package management_system.controller;

import lombok.RequiredArgsConstructor;
import management_system.domain.entity.Permission;
import management_system.domain.entity.User;
import management_system.response.ApiResponse;
import management_system.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping
    ApiResponse<List<Permission>> getAllPermisson(){
        List<Permission> permission = permissionService.getAllPermission();
        return ApiResponse.<List<Permission>>builder()
                .result(permission)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return ApiResponse.<Permission>builder()
                .result(permission)
                .build();
    }

    @GetMapping("/name/{name}")
    public ApiResponse<Permission> getPermissionByName(@PathVariable String name) {
        Permission permission = permissionService.getPermissionByName(name);
        return ApiResponse.<Permission>builder()
                .result(permission)
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<Permission> getPermissionByCode(@PathVariable String code) {
        Permission permission = permissionService.getPermissionByCode(code);
        return ApiResponse.<Permission>builder()
                .result(permission)
                .build();
    }
}
