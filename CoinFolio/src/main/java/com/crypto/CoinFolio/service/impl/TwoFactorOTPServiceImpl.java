package com.crypto.CoinFolio.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.model.TwoFactorOTP;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.TwoFactorOTPRepository;
import com.crypto.CoinFolio.service.TwoFactorOTPService;

@Service
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService{

    @Autowired
    private TwoFactorOTPRepository twoFactorOTPRepository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();

        twoFactorOTP.setOtp(otp);
        twoFactorOTP.setJwt(jwt);
        twoFactorOTP.setId(id);
        twoFactorOTP.setUser(user);

        twoFactorOTPRepository.save(twoFactorOTP);

        return twoFactorOTP;
    }

    @Override
    public TwoFactorOTP finByUserId(Long id) {
        return twoFactorOTPRepository.findByUser_Id(id);
    }

    @Override
    public TwoFactorOTP findbyId(String id) {
        Optional<TwoFactorOTP> otp = twoFactorOTPRepository.findById(id);
        return otp.orElse(null);
    }

    @Override
    public boolean varifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getOtp().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepository.delete(twoFactorOTP);
    }
    
}
