package management_system.domain.repository;

import management_system.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail (String email);
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<User> findById(Long id);
}
