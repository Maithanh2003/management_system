package management_system.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "created_by")
    private String created_by;
    @Column(name = "updated_at")
    private LocalDate updated_at;
    @Column(name = "updated_by")
    private String updated_by;
    @Column(name = "is_deleted")
    private int is_deleted =0;

    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Task> task = new HashSet<>();

    public void markAsDeleted() {
        this.is_deleted = 1;
        this.updated_at = LocalDate.now();
        this.updated_by = "system";
    }
}
