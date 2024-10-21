package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crypto.CoinFolio.model.ForgotPasswordToken;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String>{
    ForgotPasswordToken findByUserId(Long userId);
}
