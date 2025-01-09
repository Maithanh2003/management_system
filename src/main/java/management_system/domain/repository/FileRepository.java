package management_system.domain.repository;

import management_system.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findByIsDeleted(int isDeleted);
}
