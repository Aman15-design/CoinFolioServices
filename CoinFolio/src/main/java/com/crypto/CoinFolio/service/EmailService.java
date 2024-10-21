package com.crypto.CoinFolio.service;

import org.springframework.mail.javamail.JavaMailSender;


public interface EmailService {

    void sendVerificationOTPEmail(String email,String otp);

}
