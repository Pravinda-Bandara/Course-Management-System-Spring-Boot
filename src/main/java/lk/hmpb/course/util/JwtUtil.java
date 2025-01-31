package lk.hmpb.course.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${secret.key}")
    private String secretKey;  // Injected from application.properties

    private final long expiration = 100 * 60 * 1000; // 10 minutes, in milliseconds

    // Generate JWT token with username and role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Extract claims from the token
    public Claims extractClaims(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();
            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has expired", e); // Custom exception for expired tokens
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT token: " + e.getMessage(), e);
        }
    }

    // Extract role from the token
    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? claims.get("role", String.class) : null;
    }
}
