package com.crypto.CoinFolio.service.impl;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crypto.CoinFolio.config.JwtProvider;
import com.crypto.CoinFolio.domain.VerificationType;
import com.crypto.CoinFolio.model.TwoFactorAuth;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.repository.UserRepository;
import com.crypto.CoinFolio.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User findUserByJwt(String jwt) {
       String email = JwtProvider.getEmailFromJwtToken(jwt);
       User user = userRepository.findByEmail(email);
       if(user==null){
        try {
            throw new Exception("User not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
       }
       return user;
    }

    @Override
    public User findUserByEmail(String email) {
       User user = userRepository.findByEmail(email);
       if(user==null){
        try {
            throw new Exception("User not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
       }
       return user;
    }

    @Override
    public User findUserById(Long userId) {
        java.util.Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            try {
                throw new Exception("User not found");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user.get();
    }

    @Override
    public User updatePassword(User user, String newPassWord) {
        user.setPassword(newPassWord);
        return userRepository.save(user);
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactorAuth twoFactorAuth = new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setSendTo(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }
    
}
