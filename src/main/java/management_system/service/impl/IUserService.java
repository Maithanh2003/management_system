package management_system.service.impl;

import management_system.domain.entity.User;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAllUser();
    Optional<User> getUserById(Long userId);
    User createUser(CreateUserRequest request);

    User updateUser(UpdateUserRequest request, Long userId);

    void deleteUser(Long userId);
}
