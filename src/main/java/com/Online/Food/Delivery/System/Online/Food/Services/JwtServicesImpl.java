package com.Online.Food.Delivery.System.Online.Food.Services;

import com.Online.Food.Delivery.System.Online.Food.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServicesImpl implements JwtServices {
    @Value("${spring.secret.Key}")
    private String secretKey;

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public String generateAccessToken(User user) {
       return Jwts.builder()
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60*10))
                .signWith(getSecretKey())
                .compact();
    }
    @Override
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60*60))
                .signWith(getSecretKey())
                .compact();
    }
    @Override
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
//        System.out.println(claims.get("username"));
        return Long.valueOf(claims.getSubject());
    }
}
