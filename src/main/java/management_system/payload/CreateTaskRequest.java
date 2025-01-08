package management_system.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequest {
    private String name;
    @NotBlank(message = " hay dien ma code")
    private String code;
    @NotBlank(message = "khong duoc de trong project")
    private String project;
}
