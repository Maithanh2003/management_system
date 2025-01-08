package management_system.domain.repository;

import management_system.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long>{
    Optional<Role> findByName(String role_user);
    Optional<Role> findByCode(String code);
    boolean existsByCode(String code);
}
