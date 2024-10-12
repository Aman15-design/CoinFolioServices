package com.crypto.CoinFolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.CoinFolio.config.JwtProvider;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.UserRepository;
import com.crypto.CoinFolio.response.AuthResponse;
import com.crypto.CoinFolio.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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

}
