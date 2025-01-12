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
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class Config {
    private final SystemUserDetailService systemUserDetailService;
    private final JwtAuthEntryPoint authEntryPoint;
private static final List<String> PUBLIC_URLS = List.of(
        "/roles/**",
        "/projects/**",
        "/permissions/**",
        "/files",
        "/auth/login",
        "/tasks/**"
);

    private static final List<String> ADMIN_URLS = List.of(
            "/roles",
            "/roles/{id}",
            "/projects",
            "/projects/{projectId}",
            "/projects/{id}",
            "/permissions",
            "/permissions/**",
            "/files/{id}",
            "/users",
            "/users/{id}",
            "/tasks",
            "/tasks/{id}",
            "/tasks/{taskId}/assign-user",
            "/users/{id}",
            "/tasks/{taskId}/assign-user"

    );
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
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(PUBLIC_URLS.toArray(String[]::new)).permitAll();
                    auth.requestMatchers(ADMIN_URLS.toArray(String[]::new)).authenticated();
                    auth.anyRequest().permitAll();
                });
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore((Filter) authTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
