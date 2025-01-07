package management_system.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "is_deleted")
    private int is_deleted =0;
    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "created_by")
    private String created_by;
    @Column(name = "updated_at")
    private LocalDate updated_at;
    @Column(name = "updated_by")
    private String updated_by;
    @Column(name = "avatar")
    private String avatar;
    @ManyToMany
    Set< Role> role = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Project> projects = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Task> tasks = new HashSet<>();

}
