package com.rouge.rouge_springboot.util;

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
}
