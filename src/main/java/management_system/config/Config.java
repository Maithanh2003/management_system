package management_system.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import management_system.config.jwt.AuthTokenFilter;
import management_system.config.jwt.JwtAuthEntryPoint;
import management_system.config.user.SystemUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)

public class Config {
    private final SystemUserDetailService systemUserDetailService;
    private final JwtAuthEntryPoint authEntryPoint;
    private static final List<String> SECURED_URLS =
            List.of("/api/v1/carts/**", "/api/v1/cartItems/**");
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public ModelMapper modelMapper (){
        return new ModelMapper();
    }
    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }
    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(systemUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
//        sau khi nhận được thông tin người dùng (đối tượng UserDetails), DaoAuthenticationProvider sẽ lấy mật khẩu mà người dùng nhập vào và so sánh với mật khẩu đã mã hóa từ cơ sở dữ liệu (được lưu trong đối tượng UserDetails).
//        Nếu mật khẩu hợp lệ (sử dụng PasswordEncoder để kiểm tra), người dùng sẽ được xác thực thành công.
//                Xử lý quyền (Authorities):
//        Sau khi xác thực thành công, Spring sẽ lưu trữ thông tin xác thực và các quyền của người dùng trong SecurityContext, để có thể sử dụng cho các yêu cầu tiếp theo (ví dụ kiểm tra quyền truy cập vào các tài nguyên bảo mật).
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    // Cho phép truy cập không cần xác thực vào các endpoint giao diện đăng nhập
                    auth.requestMatchers("auth/login", "auth/success").permitAll();
                    // Yêu cầu xác thực cho các URL đã cấu hình
                    auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated();
                    // Các request còn lại được phép truy cập
                    auth.anyRequest().permitAll();
                });
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore((Filter) authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
}
