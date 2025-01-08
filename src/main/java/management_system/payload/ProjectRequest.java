package management_system.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    @NotBlank(message = "yeu cau nhap ten")
    private String name;
    @NotBlank(message = "yeu cau nhap ma code")
    private String code;

}
