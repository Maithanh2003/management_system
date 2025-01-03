package management_system.controller;

import lombok.RequiredArgsConstructor;
import management_system.domain.entity.Permission;
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
    ResponseEntity<List<Permission>> getAllPermisson(){
        List<Permission> permission = permissionService.getAllPermission();
        return ResponseEntity.ok(permission);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Permission permission = permissionService.getPermissionById(id);
        return ResponseEntity.ok(permission);
    }


    @GetMapping("/name/{name}")
    public ResponseEntity<Permission> getPermissionByName(@PathVariable String name) {
        Permission permission = permissionService.getPermissionByName(name);
        return ResponseEntity.ok(permission);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Permission> getPermissionByCode(@PathVariable String code) {
        Permission permission = permissionService.getPermissionByCode(code);
        return ResponseEntity.ok(permission);
    }
}
