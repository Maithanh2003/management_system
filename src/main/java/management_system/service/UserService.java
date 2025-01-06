package management_system.service;

import ch.qos.logback.classic.encoder.JsonEncoder;
import lombok.RequiredArgsConstructor;
import management_system.domain.entity.Role;
import management_system.domain.entity.User;
import management_system.domain.repository.RoleRepository;
import management_system.domain.repository.UserRepository;
import management_system.exception.AlreadyExistsException;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.CreateUserRequest;
import management_system.payload.UpdateUserRequest;
import management_system.service.impl.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("not found user"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }
        Role defaultRole = roleRepository.findByName("User").orElseThrow(
                () -> new ResourceNotFoundException("Default role not found")
        );

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreated_at(LocalDate.now());
        user.setCreated_by("System");
        user.setRoles(Set.of(defaultRole));

        return userRepository.save(user);
    }
    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " +userId)
        );

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUpdated_at(LocalDate.now());
        user.setUpdated_by("System");

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with ID: " + userId)
        );

        // Xóa mềm (Soft delete)
        user.setIs_deleted(1);
        user.setUpdated_at(LocalDate.now());
        user.setUpdated_by("System");
        userRepository.save(user);
    }
}
