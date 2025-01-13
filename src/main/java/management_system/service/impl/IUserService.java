package management_system.service.impl;

import management_system.domain.dto.UserDTO;
import management_system.domain.entity.User;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> getAllUser();
    Optional<UserDTO> getUserById(Long userId);
    UserDTO getUserByEmail(String email);
    UserDTO createUser(CreateUserRequest request);

    UserDTO updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);
}
