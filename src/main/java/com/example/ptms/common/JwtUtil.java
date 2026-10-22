package com.example.ptms.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey key;
    private final long expireHours;
    public JwtUtil(@Value("${jwt.secret}") String secret,
                    @Value("${jwt.expire-hours}") long expireHours){
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireHours = expireHours;
    }

    public String generate(Long userId,String username){
        Date now = new Date();
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("username",username)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expireHours * 3600_000))
                .signWith(key)
                .compact();
    }

    public Long parseUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Long.valueOf(claims.getSubject());
    }
}
