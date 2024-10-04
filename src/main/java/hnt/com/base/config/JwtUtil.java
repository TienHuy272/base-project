package hnt.com.base.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hnt.com.base.model.ClaimsToken;
import hnt.com.base.model.LoginUserInfo;
import hnt.com.base.model.TokenModel;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {
    @Value("${application.jwt.secret.key}")
    private String APPLICATION_JWT_SECRET_KEY;
    @Value("${application.jwt.expiration.time}")
    private long APPLICATION_JWT_EXPIRATION_TIME;
    @Value("${application.jwt.expiration.refresh.time}")
    private long APPLICATION_JWT_EXPIRATION_REFRESH_TIME;

    @Autowired
    private ObjectMapper objectMapper;

    public TokenModel generateToken(Authentication authentication) {
        LoginUserInfo principal = (LoginUserInfo) authentication.getPrincipal();
        TokenModel model = new TokenModel();
        model.setToken(generateTokenByAuthentication(principal));
        model.setRefreshToken(generateTokenByAuthentication(principal, APPLICATION_JWT_EXPIRATION_REFRESH_TIME));
        return model;
    }

    private String generateTokenByAuthentication(LoginUserInfo principal) {
        return generateTokenByAuthentication(principal, APPLICATION_JWT_EXPIRATION_TIME);
    }

    private String generateTokenByAuthentication(LoginUserInfo principal, long expiredTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expiredTime);

        ClaimsToken model = new ClaimsToken();
        model.setEmail(principal.getEmail());
        model.setUsername(principal.getUsername());
        for (GrantedAuthority authority : principal.getAuthorities()) {
            model.getRoles().add(authority.getAuthority());
        }

        String subject = principal.getUsername();
        Map<String, Object> claims = new HashMap<>();
        claims.put("model", model);

        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, APPLICATION_JWT_SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(APPLICATION_JWT_SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(APPLICATION_JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public ClaimsToken getClaimsToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(APPLICATION_JWT_SECRET_KEY).parseClaimsJws(token).getBody();
            return objectMapper.convertValue(claims.get("model"), ClaimsToken.class);
        } catch (Exception e) {
            return null;
        }
    }
}
