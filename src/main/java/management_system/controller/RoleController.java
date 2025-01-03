package management_system.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.domain.entity.Role;
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
    public ResponseEntity<Role> getRoleById(@PathVariable("id") Long roleId) {
        Role role = roleService.getRoleById(roleId);
        if (role!= null) {
            log.info(role.getId().toString());
            return ResponseEntity.ok(role);
        } else {
            throw new ResourceNotFoundException("Role not found with ID: " + roleId);
        }
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Role> getRoleByCode(@PathVariable("code") String code) {
        Role role = roleService.getRoleByCode(code);
        if (role != null) {
            log.info(role.getId().toString());
            return ResponseEntity.ok(role);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable("name") String name) {
        Role role = roleService.getRoleByName(name);
        if (role != null) {
            log.info(role.getId().toString());
            return ResponseEntity.ok(role);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
