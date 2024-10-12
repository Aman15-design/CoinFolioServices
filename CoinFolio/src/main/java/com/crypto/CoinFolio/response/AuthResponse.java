package com.crypto.CoinFolio.response;

import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private boolean status;
    private String message;
    private boolean error;
    private boolean isTwoFactorAuthEnabled;
    private String session;
}
