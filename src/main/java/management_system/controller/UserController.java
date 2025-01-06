package management_system.controller;

import lombok.RequiredArgsConstructor;
import management_system.domain.entity.User;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import management_system.response.ApiResponse;
import management_system.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody CreateUserRequest request) {
        try {
            User savedUser = userService.createUser(request);
            return ApiResponse.<User>builder()
                    .result(savedUser)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<User>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        try {
            List<User> user = userService.getAllUser();
            return ApiResponse.<List<User>>builder()
                    .result(user)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<User>>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ApiResponse.<User>builder()
                .result(user)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            User updatedUser = userService.updateUser(request, id);
            return ApiResponse.<User>builder()
                    .result(updatedUser)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<User>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ApiResponse.<Void>builder()
                    .message("User deleted successfully")
                    .code(204)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<Void>builder()
                    .message("error")
                    .code(404)
                    .build();
        }
    }
}
