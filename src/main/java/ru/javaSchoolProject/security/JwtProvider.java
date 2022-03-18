package ru.javaSchoolProject.security;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log
public class JwtProvider {

    @Value("TopSecretValue")
    private String jwtSecret;

    @Value("360000000")
    private long tokenDurabilityInMilliseconds = 360000000;

    public String generateToken(String login) {
        //set token expiration time
        Date now = new Date();
        Date tokenExpirationTime = new Date(now.getTime() + tokenDurabilityInMilliseconds);
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(tokenExpirationTime)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature");
        } catch (Exception e) {
            System.out.println("invalid token");
//            log.severe
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }


}
