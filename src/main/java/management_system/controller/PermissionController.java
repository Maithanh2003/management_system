package management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.domain.constant.ResponseConstants;
import management_system.domain.dto.PermissionDTO;
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
    ApiResponse<List<PermissionDTO>> getAllPermisson() {
        List<PermissionDTO> permission = permissionService.getAllPermission();
        return ApiResponse.<List<PermissionDTO>>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(permission)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<PermissionDTO> getPermissionById(@PathVariable Long id) {
        PermissionDTO permission = permissionService.getPermissionById(id);
        return ApiResponse.<PermissionDTO>builder()
                .result(permission)
                .build();
    }

    @GetMapping("/name/{name}")
    public ApiResponse<PermissionDTO> getPermissionByName(@PathVariable String name) {
        PermissionDTO permission = permissionService.getPermissionByName(name);
        return ApiResponse.<PermissionDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(permission)
                .build();
    }

    @GetMapping("/code/{code}")
    public ApiResponse<PermissionDTO> getPermissionByCode(@PathVariable String code) {
        PermissionDTO permission = permissionService.getPermissionByCode(code);
        return ApiResponse.<PermissionDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(permission)
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<PermissionDTO> createPermission(@Valid @RequestBody PermissionRequest request) {
        PermissionDTO permission = permissionService.createPermission(request);
        return ApiResponse.<PermissionDTO>builder()
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
