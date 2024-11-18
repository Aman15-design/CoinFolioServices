package com.crypto.CoinFolio.request;

import com.crypto.CoinFolio.domain.VerificationType;

import lombok.Data;

@Data
public class ForgotPasswordTokenRequest {
    private String sendTo;
    private VerificationType verificationType;
}
