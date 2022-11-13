package xyz.fmdb.fmdb.util;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import xyz.fmdb.fmdb.models.Account;

import java.util.Date;


@Component
public class JwtUtils {

    @Value("${fmdb.jwt.secret}")
    private String jwtSecret;

    @Value("${fmdb.jwt.expirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Account account) {
        return Jwts.builder()
                .setSubject(String.valueOf(account.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature: "+ e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: "+ e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: "+ e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: "+ e.getMessage());
        }

        return false;
    }
}
