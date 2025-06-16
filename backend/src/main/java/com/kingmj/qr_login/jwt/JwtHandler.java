package com.kingmj.qr_login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtHandler {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMillis;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String userId) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(makeClaim(userId))
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public String validateTokenAndGetUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.get("userId", String.class);

        } catch (SecurityException | MalformedJwtException e) {
            throw new RuntimeException("서명 실패", e);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("만료된 토큰", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("토큰이 비어있음", e);
        }
    }

    private Map<String, Object> makeClaim(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);

        return claims;
    }


}
