package management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.domain.constant.ResponseConstants;
import management_system.domain.entity.Permission;
import management_system.domain.entity.User;
import management_system.payload.PermissionRequest;
import management_system.response.ApiResponse;
import management_system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private final PermissionService permissionService;

    @GetMapping
    ApiResponse<List<Permission>> getAllPermisson() {
        List<Permission> permission = permissionService.getAllPermission();
        return ApiResponse.<List<Permission>>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
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
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(permission)
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<Permission> getPermissionByCode(@PathVariable String code) {
        Permission permission = permissionService.getPermissionByCode(code);
        return ApiResponse.<Permission>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(permission)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<Permission> createPermission(@Valid @RequestBody PermissionRequest request) {
        Permission permission = permissionService.createPermission(request);
        return ApiResponse.<Permission>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .message("tao permission thanh cong")
                .result(permission).build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePermissionById(@PathVariable Long id) {
        permissionService.deletePermissionById(id);
        return ApiResponse.<Void>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .message("xoa permission thanh cong voi id " + id)
                .result(null).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/by-code/{code}")
    public ApiResponse<Void> deletePermissionByCode(@PathVariable String code) {
        permissionService.deletePermissionByCode(code);
        return ApiResponse.<Void>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .message("xoa permission thanh cong voi code " + code)
                .result(null).build();
    }
}
