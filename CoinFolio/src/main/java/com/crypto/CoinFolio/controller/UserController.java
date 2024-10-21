package com.crypto.CoinFolio.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.crypto.CoinFolio.service.VerificationCodeService;
import com.crypto.CoinFolio.Utils.OtpUtils;
import com.crypto.CoinFolio.domain.VerificationType;
import com.crypto.CoinFolio.model.ForgotPasswordToken;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.model.VerificationCode;
import com.crypto.CoinFolio.request.ForgotPasswordTokenRequest;
import com.crypto.CoinFolio.request.ResetPasswordRequest;
import com.crypto.CoinFolio.response.ApiResponse;
import com.crypto.CoinFolio.response.AuthResponse;
import com.crypto.CoinFolio.service.EmailService;
import com.crypto.CoinFolio.service.ForgotPasswordService;
import com.crypto.CoinFolio.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profiles")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwt(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) {
        User user = userService.findUserByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        if (verificationCode == null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        } else {
            if (verificationType.equals(verificationType.EMAIL)) {
                emailService.sendVerificationOTPEmail(user.getEmail(), verificationCode.getOtp());
            } else {

            }
        }
        return new ResponseEntity<>("Verificaiton OTP sent successfully", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@RequestHeader("Authorization") String jwt,
            @PathVariable String otp) throws Exception {
        User user = userService.findUserByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUser(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)
                ? verificationCode.getEmail()
                : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo,
                    user);

            verificationCodeService.deleteVerificationCodeById(verificationCode);

            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Wrong otp");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) {
        
        User user = userService.findUserByEmail(forgotPasswordTokenRequest.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if(token == null){
            token = forgotPasswordService.createToken(user, id, forgotPasswordTokenRequest.getVerificationType(), forgotPasswordTokenRequest.getSendTo());
        }

        if(forgotPasswordTokenRequest.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(), token.getOtp());
        }
        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password sent successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp/")
    public ResponseEntity<ApiResponse> resetPassword(@RequestHeader("Authorization") String jwt,@RequestBody ResetPasswordRequest resetPasswordRequest,@RequestParam String id) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(resetPasswordRequest.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), resetPasswordRequest.getPassword());
            ApiResponse response = new ApiResponse();
            response.setMessage("Password Updated Successfully");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        throw new Exception("Invalid OTP");
    }
}
