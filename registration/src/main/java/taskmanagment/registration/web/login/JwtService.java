package taskmanagment.registration.web.login;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public  class JwtService {
    private final static String secret = "my-super-secret-key-my-super-secret-key";

    private static Key getSigningKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public static String generateToken(String username){

        long now = System.currentTimeMillis();
        long exp = now + 360000;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(exp))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
