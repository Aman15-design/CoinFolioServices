package com.crypto.CoinFolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {
    
    @Value("${jwt.secret.key}")
    public String SECRET_KEY;

    public static final String JWT_HEADER = "Authorization";
}
