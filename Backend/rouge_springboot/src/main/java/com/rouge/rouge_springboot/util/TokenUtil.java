package com.rouge.rouge_springboot.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class TokenUtil {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public static String generateToken(Long userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 3)) // 3 hour
                .signWith(SECRET_KEY)
                .compact();
    }


    public static String getUserIdFromToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);

            return jws.getPayload().getSubject();
        } catch (JwtException e) {
            // 处理无效令牌的情况
            throw new RuntimeException("Invalid or expired token", e);
        }
    }

    public static Boolean verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
