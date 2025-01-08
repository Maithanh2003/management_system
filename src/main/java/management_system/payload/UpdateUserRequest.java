package management_system.payload;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserRequest {
    private String name;
    private String email;
    private String avatar;
}
