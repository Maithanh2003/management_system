package management_system.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
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
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    Set<Permission> permission;

    public Long getId() {
        return id;
    }
    public void markAsDeleted() {
        this.isDeleted = 1;
        this.updatedAt = LocalDate.now();
        this.updatedBy = "system";
    }

}
