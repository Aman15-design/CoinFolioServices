package com.crypto.CoinFolio.config;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtProvider {
    private static SecretKey key = Keys.hmacShaKeyFor(JwtConstant.getSecretKey().getBytes());

    public static String generateToken(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        String roles = populateAuthorities(authorities);

        // Generate JWT token without expiration date
        String jwt = Jwts.builder()
                .claim("email", auth.getName()) // Add email claim
                .claim("authorities", roles) // Add authorities claim
                .signWith(key, SignatureAlgorithm.HS256) // Sign with key and HS256 algorithm
                .compact();
        
        return jwt;
    }

    private static String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> auth = new HashSet<>();
        for(GrantedAuthority ga : authorities){
            auth.add(ga.getAuthority());
        }
        return String.join(",", auth);
    }

    public static String getEmailFromJwtToken(String token){
        token = token.substring(7); // Remove "Bearer " prefix
        Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        String email = String.valueOf(claims.get("email"));
        return email;
    }
}
