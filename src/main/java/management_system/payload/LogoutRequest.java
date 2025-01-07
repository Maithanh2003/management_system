package management_system.payload;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutRequest {
    private String token;
}
