package management_system.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
