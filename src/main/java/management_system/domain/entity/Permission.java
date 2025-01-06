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
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code",nullable = false, unique = true)
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
}
