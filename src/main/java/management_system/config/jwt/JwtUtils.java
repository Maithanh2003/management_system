package management_system.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import management_system.config.user.SystemUserDetails;
import management_system.domain.entity.Permission;
import management_system.domain.entity.Role;
import management_system.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtUtils {
    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;
    @Value("${auth.token.expirationInMils}")
    private int expirationTime;
    @Value("${auth.token.refreshexpirationInMils}")
    private int refreshExpirationTime;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Autowired
    private final RoleRepository roleRepository;

    public JwtUtils(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public String generateToken (Authentication authentication){
        SystemUserDetails userPrincipal = (SystemUserDetails) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).toList();
        String jwtId = UUID.randomUUID().toString();
        Long expirationTimeToken = Long.valueOf(expirationTime);
        List<String> permissions = roles.stream()
                .flatMap(roleName -> {
                    Role role = findRoleByName(roleName);
                    return role.getPermission()
                            .stream()
                            .map(Permission::getCode);
                })
                .distinct()
                .toList();
        
        return Jwts.builder()
                .setId(jwtId)
                .setSubject(userPrincipal.getEmail())
                .claim("id", userPrincipal.getId())
                .claim("roles", roles)
                .claim("permissions", permissions)
                .setExpiration(new Date((new Date()).getTime() +expirationTimeToken))
                .signWith(key(), SignatureAlgorithm.HS256).compact();

    }

    private Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
    }

    private Key key (){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUsernameFromToken( String token){
        return Jwts.parserBuilder().setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
    public String getIdFromToken( String token){
        return Jwts.parserBuilder().setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody().getId();
    }
    public Date getExpirationFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken (String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false; // Token đã hết hạn
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            throw new JwtException(e.getMessage());
        }
    }
}
