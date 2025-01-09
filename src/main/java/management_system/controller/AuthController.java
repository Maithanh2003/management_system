package management_system.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import management_system.config.jwt.JwtUtils;
import management_system.config.user.SystemUserDetails;
import management_system.domain.entity.User;
import management_system.domain.repository.UserRepository;
import management_system.exception.ResourceNotFoundException;
import management_system.payload.LoginRequest;
import management_system.payload.LogoutRequest;
import management_system.response.ApiResponse;
import management_system.response.JwtResponse;
import management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(authentication.getAuthorities().toString());
            String jwt = jwtUtils.generateToken(authentication);
            SystemUserDetails userDetails = (SystemUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(ApiResponse.builder().message("login success").result(jwtResponse).build());
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.builder().message("xay ra loi").result(null).build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder().message("An unexpected error occurred").result(null).build());
        }
    }

}
