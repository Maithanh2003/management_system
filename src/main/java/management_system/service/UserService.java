package management_system.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management_system.config.user.SystemUserDetails;
import management_system.domain.dto.UserDTO;
import management_system.domain.entity.Role;
import management_system.domain.entity.User;
import management_system.domain.repository.RoleRepository;
import management_system.domain.repository.UserRepository;
import management_system.exception.AlreadyExistsException;
import management_system.exception.ResourceNotFoundException;
import management_system.mapper.UserMapper;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import management_system.service.impl.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
//    @Autowired
//    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public List<UserDTO> getAllUser() {
        return userRepository.findAll().stream().map(users -> userMapper.toUserDTO(users)).toList();
    }

    @Override
    public Optional<UserDTO> getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " + userId)
        );
        return Optional.ofNullable(userMapper.toUserDTO(user));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("not found user"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO createUser( CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        Role defaultRole = roleRepository.findByName("ROLE_USER").orElseThrow(
                () -> new ResourceNotFoundException("Default role not found")
        );

        User user = userMapper.toUser(request);
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setCreatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        user.setCreatedBy(userPrincipal.getEmail());
        user.setRole(Set.of(defaultRole));
        userRepository.save(user);
        UserDTO userDTO = userMapper.toUserDTO(user);
        return userDTO;
    }
    @Override
    public UserDTO updateUser(UpdateUserRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " +userId)
        );

//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setUpdatedAt(LocalDate.now());
        userMapper.updateUserFromRequest(request, user);
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        user.setUpdatedBy(userPrincipal.getEmail());
        userRepository.save(user);
        return userMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " + userId)
        );

        // Xóa mềm (Soft delete)
        user.setIsDeleted(1);
        user.setUpdatedAt(LocalDate.now());
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        user.setUpdatedBy(userPrincipal.getEmail());
        userRepository.save(user);
    }
//    public UserDTO convertToDto(User user) {
//        return modelMapper.map(user, UserDTO.class);
//    }
//
//    public User convertToEntity(UserDTO userDto) {
//        return modelMapper.map(userDto, User.class);
//    }
}
