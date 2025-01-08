package management_system.controller;

import lombok.RequiredArgsConstructor;
import management_system.domain.dto.UserDTO;
import management_system.domain.entity.User;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import management_system.response.ApiResponse;
import management_system.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserDTO> createUser(@RequestBody CreateUserRequest request) {
        try {
            User savedUser = userService.createUser(request);
            UserDTO userDTO = userService.convertToDto(savedUser);
            return ApiResponse.<UserDTO>builder()
                    .result(userDTO)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserDTO>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> getAllUsers() {
        try {
            List<User> user = userService.getAllUser();
            List<UserDTO> userDTOS = user.stream().map(users -> userService.convertToDto(users)).toList();
            return ApiResponse.<List<UserDTO>>builder()
                    .result(userDTOS)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<UserDTO>>builder()
                    .result(null)
                    .message("error")
                    .code(404)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        UserDTO UserDto = userService.convertToDto(user);
        return ApiResponse.<UserDTO>builder()
                .result(UserDto)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDTO> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            User updatedUser = userService.updateUser(request, id);
            UserDTO UserDto = userService.convertToDto(updatedUser);
            return ApiResponse.<UserDTO>builder()
                    .result(UserDto)
                    .build();
        } catch (Exception e) {
            return ApiResponse.<UserDTO>builder()
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
