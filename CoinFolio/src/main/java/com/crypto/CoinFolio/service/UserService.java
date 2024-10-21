package com.crypto.CoinFolio.service;

import com.crypto.CoinFolio.domain.VerificationType;
import com.crypto.CoinFolio.model.User;

public interface UserService {
    public User findUserByJwt(String jwt);
    public User findUserByEmail(String email);
    public User findUserById(Long userId);
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);
    public User updatePassword(User user,String newPassWord);
}
