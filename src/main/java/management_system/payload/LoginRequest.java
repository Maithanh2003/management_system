package management_system.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "yeu cau nhap email")
    private String email;
    @NotBlank(message = "yeu cau nhap password")
    private String password;
}
