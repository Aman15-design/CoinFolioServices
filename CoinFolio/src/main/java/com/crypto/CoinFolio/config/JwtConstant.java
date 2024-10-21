package com.crypto.CoinFolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {
    
    // Read the secret key from environment variables or properties file
    @Value("${jwt.secret}")
    private static String secretKey;

    public static final String JWT_HEADER = "Authorization";

    // Getter for SECRET_KEY
    public static String getSecretKey() {
        return secretKey;
    }
}
