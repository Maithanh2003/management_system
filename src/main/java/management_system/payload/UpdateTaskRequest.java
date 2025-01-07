package management_system.payload;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateTaskRequest {
    private String name;
    private String code;
}
