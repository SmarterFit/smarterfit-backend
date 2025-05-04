package com.smarterfit.common.util;

import java.security.Key;
import java.sql.Date;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.smarterfit.common.dto.response.JwtToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
   private static String JWT_KEY;

   @Autowired
   public JwtUtil(@Value("${jwt.key}") String jwtKey) {
      JWT_KEY = jwtKey;
   }

   public static JwtToken generateToken(String username) {
      String tokenType = getTokenType();
      Date issuedAt = new Date(System.currentTimeMillis());
      Date expiresIn = getExpiresIn(issuedAt);

      String token = Jwts.builder()
            .subject(username)
            .issuedAt(issuedAt)
            .expiration(expiresIn)
            .signWith(getKey())
            .compact();

      JwtToken jwtToken = new JwtToken();
      jwtToken.setToken(token);
      jwtToken.setType(tokenType);

      return jwtToken;
   }

   public static String getTokenType() {
      return "Bearer";
   }

   public static Date getExpiresIn(Date date) {
      Integer timeToExpire = 1000 * 60 * 60 * 24 * 7; // 7 days
      return new Date(date.getTime() + timeToExpire);
   }

   private static Key getKey() {
      byte[] keyBytes = Base64.getDecoder().decode(JWT_KEY);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}
