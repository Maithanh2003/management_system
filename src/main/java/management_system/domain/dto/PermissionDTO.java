package management_system.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {
    private Long id;
    private String name;
    private String code;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private int isDeleted ;
}
