package com.crypto.CoinFolio.service.impl;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender javaMailSender;

    @Override
    public void sendVerificationOTPEmail(String email, String otp) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setSubject("Your CoinFolio Account : Access code");
            mimeMessageHelper.setText("Verification code : " + otp);
            mimeMessageHelper.setTo(email);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MailSendException("Unable to send varification code");
        }
    }

}
