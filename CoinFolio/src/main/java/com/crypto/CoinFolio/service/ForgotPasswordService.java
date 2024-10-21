package com.crypto.CoinFolio.service;

import com.crypto.CoinFolio.domain.VerificationType;
import com.crypto.CoinFolio.model.ForgotPasswordToken;
import com.crypto.CoinFolio.model.User;

public interface ForgotPasswordService{
    
    ForgotPasswordToken createToken(User user,String id,VerificationType verificationType,String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userId);

    void deleteToken(ForgotPasswordToken token);
}
