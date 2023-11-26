package com.clone.config;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenProvider {

    String generateToken(Authentication authentication) {
        return String.valueOf(Jwts.builder()
                .setIssuer("Cloner")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400L))
                .claim("email", authentication.getName())
                .signWith(null)
                .compact());
    }

}
