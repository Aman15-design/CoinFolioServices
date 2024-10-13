package com.crypto.CoinFolio.service;

import com.crypto.CoinFolio.model.TwoFactorOTP;
import com.crypto.CoinFolio.model.User;

public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOtp(User user,String otp,String jwt);

    TwoFactorOTP finByUserId(Long id);

    TwoFactorOTP findbyId(String id);

    boolean varifyTwoFactorOtp(TwoFactorOTP twoFactorOTP,String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

}
