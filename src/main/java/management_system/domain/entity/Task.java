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
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code",nullable = false, unique = true)
    private String code;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "is_deleted")
    private int isDeleted =0;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    public void markAsDeleted() {
        this.isDeleted = 1;
        this.updatedAt = LocalDate.now();
        this.updatedBy = "system";
    }
}
