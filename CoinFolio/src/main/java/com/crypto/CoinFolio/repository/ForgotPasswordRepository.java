package com.crypto.CoinFolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crypto.CoinFolio.model.ForgotPasswordToken;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String>{
    ForgotPasswordToken findByUserId(Long userId);
}
