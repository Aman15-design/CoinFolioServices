package com.crypto.CoinFolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.CoinFolio.Utils.OtpUtils;
import com.crypto.CoinFolio.config.JwtProvider;
import com.crypto.CoinFolio.model.TwoFactorOTP;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.UserRepository;
import com.crypto.CoinFolio.response.AuthResponse;
import com.crypto.CoinFolio.service.CustomUserDetailsService;
import com.crypto.CoinFolio.service.EmailService;
import com.crypto.CoinFolio.service.TwoFactorOTPService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactorOTPService twoFactorOTPService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User isEmailExistUser = userRepository.findByEmail(user.getEmail());
        if (isEmailExistUser != null) {
            throw new Exception("Email already in use");
        } else {
            User savedUser = userRepository.save(newUser);
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwt = JwtProvider.generateToken(auth);
            AuthResponse response = new AuthResponse();
            response.setJwt(jwt);
            response.setStatus(true);
            response.setMessage("Register Successful");
            return new ResponseEntity<>(response, HttpStatus.CREATED); // we created new user here therefore
                                                                       // HTTPStatus.CREATED is used
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {
        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);
        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse responseTwoFactor = new AuthResponse();
            responseTwoFactor.setMessage("Two factor auth is enabled");
            responseTwoFactor.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();

            User AuthUser = userRepository.findByEmail(user.getEmail());

            TwoFactorOTP oldTwoFactorOTP = twoFactorOTPService.finByUserId(AuthUser.getId());

            if(oldTwoFactorOTP!=null){
                twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOTP);
            }

            TwoFactorOTP newTwoFactorOtp = twoFactorOTPService.createTwoFactorOtp(AuthUser, otp, jwt);

            emailService.sendVerificationOTPEmail(userName,otp);

            responseTwoFactor.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(responseTwoFactor,HttpStatus.ACCEPTED);
        }
        AuthResponse response = new AuthResponse();
        response.setJwt(jwt);
        response.setStatus(true);
        response.setMessage("Login Successful");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    private Authentication authenticate(String userName, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new Exception("Invalid UserName or Password");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new Exception("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    public ResponseEntity<AuthResponse> verifySignInOtp(@PathVariable String otp,@RequestParam String id) throws Exception{
        TwoFactorOTP twoFactorOTP = twoFactorOTPService.findbyId(id);
        if(twoFactorOTPService.varifyTwoFactorOtp(twoFactorOTP, otp)){
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("OTP Authentication Verified");
            authResponse.setTwoFactorAuthEnabled(true);
            authResponse.setJwt(twoFactorOTP.getJwt());
            return new ResponseEntity<>(authResponse,HttpStatus.OK);
        } else {
            throw new Exception("Invalid OTP");
        }
    }

}
