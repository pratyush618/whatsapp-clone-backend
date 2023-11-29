package com.clone.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@SuppressWarnings("unused")
public class TokenProvider {

    SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        return String.valueOf(Jwts.builder()
                .setIssuer("Cloner")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400L))
                .claim("email", authentication.getName())
                .signWith(null)
                .compact());
    }

    public String getEmailFromToken(String token) {
        token = token.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        return String.valueOf(claims.get("username"));
    }

}
