package com.crypto.CoinFolio.model;

import com.crypto.CoinFolio.domain.VerificationType;

import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
