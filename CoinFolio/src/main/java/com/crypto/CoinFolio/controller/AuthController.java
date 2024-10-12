package com.crypto.CoinFolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser,HttpStatus.CREATED); // we created new user here therefore HTTPStatus.CREATED is used
    }
}
