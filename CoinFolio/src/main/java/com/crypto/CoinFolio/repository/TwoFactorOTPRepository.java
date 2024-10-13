package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crypto.CoinFolio.model.TwoFactorOTP;


public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP,String> {
    TwoFactorOTP findByUser_Id(long userId);
}
