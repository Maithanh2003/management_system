package management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.domain.constant.ResponseConstants;
import management_system.domain.dto.UserDTO;
import management_system.domain.entity.User;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import management_system.response.ApiResponse;
import management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Autowired
    private final UserService userService;

    @PostMapping
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            UserDTO savedUser = userService.createUser(request);
            return ApiResponse.<UserDTO>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .result(savedUser)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserDTO>builder()
                    .result(null)
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }

    @Transactional
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserDTO>> getAllUsers() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                log.info("Current User Roles: {}", authentication.getAuthorities());
            } else {
                log.info("No authenticated user.");
            }
            List<UserDTO> user = userService.getAllUser();
            return ApiResponse.<List<UserDTO>>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .result(user)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<UserDTO>>builder()
                    .result(null)
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }

    @PostAuthorize("hasRole('ADMIN') || returnObject.result.email == authentication.principal.username")
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ApiResponse.<UserDTO>builder()
                .code(ResponseConstants.SUCCESS_CODE)
                .message(ResponseConstants.SUCCESS_MESSAGE)
                .result(user)
                .build();
    }

    @PostAuthorize("hasRole('ADMIN') || returnObject.result.email == authentication.principal.username")
    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            UserDTO updatedUser = userService.updateUser(request, id);
            return ApiResponse.<UserDTO>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .result(updatedUser)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserDTO>builder()
                    .result(null)
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.SUCCESS_CODE)
                    .message(ResponseConstants.SUCCESS_MESSAGE)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .code(ResponseConstants.ERROR_CODE)
                    .message(ResponseConstants.ERROR_MESSAGE)
                    .build();
        }
    }
}
