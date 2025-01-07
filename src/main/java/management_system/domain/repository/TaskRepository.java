package management_system.domain.repository;

import management_system.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository< Task, Long> {
    Optional<List<Task>> findByUserId(Long userId);
    Optional<List<Task>> findByProjectId(Long userId);
}

