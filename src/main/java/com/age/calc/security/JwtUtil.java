package com.age.calc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    // Clé secrète (en vrai projet: à mettre hors du code)
    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final long DUREE_MS = 24L * 60 * 60 * 1000; // 24 h

    public String generer(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + DUREE_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }


    public String verifier(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
