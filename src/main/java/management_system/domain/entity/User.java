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

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }
}
