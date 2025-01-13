package management_system.mapper;

import lombok.RequiredArgsConstructor;
import management_system.domain.dto.UserDTO;
import management_system.domain.entity.User;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@RequiredArgsConstructor
public class UserMapper {
    @Autowired
    private final ModelMapper modelMapper;
    public UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    // Convert CreateUserRequest to User
    public User toUser(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);
        user.setCreatedAt(LocalDate.now());
        return user;
    }

    // Convert UpdateUserRequest to User (only updating fields)
    public void updateUserFromRequest(UpdateUserRequest updateUserRequest, User user) {
        if (updateUserRequest.getName() != null) {
            user.setName(updateUserRequest.getName());
        }
        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        if (updateUserRequest.getAvatar() != null) {
            user.setAvatar(updateUserRequest.getAvatar());
        }
        user.setUpdatedAt(LocalDate.now());
    }

    // Convert User to a new instance (e.g., cloning without sensitive data)
    public User toClonedUser(User user) {
        User clonedUser = modelMapper.map(user, User.class);
        clonedUser.setPassword(null); // Avoid cloning sensitive fields
        return clonedUser;
    }
}
