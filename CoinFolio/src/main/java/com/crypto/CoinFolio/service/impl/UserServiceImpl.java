package com.crypto.CoinFolio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.UserRepository;
import com.crypto.CoinFolio.service.UserService;

public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User findUserByJwt(String jwt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserByJwt'");
    }

    @Override
    public User findUserByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserByEmail'");
    }

    @Override
    public User findUserById(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserById'");
    }

    @Override
    public User enableTwoFactorAuthentication(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enableTwoFactorAuthentication'");
    }

    @Override
    public User updatePassword(User user, String newPassWord) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
    }
    
}
