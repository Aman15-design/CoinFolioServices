package com.crypto.CoinFolio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {
    

    public static String SECRET_KEY = "asfnbpngonpgowengponerbhnpoiohfqiwfnbvooiewbgvip0o2b";

    public static final String JWT_HEADER = "Authorization";
}
