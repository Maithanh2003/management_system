package management_system.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "file_name",nullable = false)
    private String fileName;
    @Column(name = "uploaded_at")
    private LocalDate uploadedAt;
    @Column(name = "uploaded_by")
    private String uploadedBy;
    @Column(name = "is_deleted")
    private int isDeleted =0;
    public void markAsDeleted() {
        this.isDeleted = 1;
    }
}
