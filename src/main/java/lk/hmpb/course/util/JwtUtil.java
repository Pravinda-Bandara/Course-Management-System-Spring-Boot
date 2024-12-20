package lk.hmpb.course.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secretKey = "G2aKmHh9s24jKla2bBh4JSkj12l0a1Fsonoando2aon2odnaosndononoaubdaobsdkbiuabsbdkabwubkdsbakjwudakskjbxkawduauhsndkuawbudabskbdakuwduabskdbwkubaksdkbwadbkawbdkudbibawdibasbdwibaixbciawbidbsiu"; // Securely stored hard-coded secret key
    private final long expiration = 10 * 60 * 1000; // 10 minutes, in milliseconds

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build();
            return parser.parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JWT token: " + e.getMessage(), e);
        }
    }

    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims != null ? claims.get("role", String.class) : null;
    }
}
