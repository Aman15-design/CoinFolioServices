package com.crypto.CoinFolio.service;

import com.crypto.CoinFolio.domain.VerificationType;
import com.crypto.CoinFolio.model.User;
import com.crypto.CoinFolio.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id);

    VerificationCode getVerificationCodeByUser(Long userId);

    void deleteVerificationCodeById(VerificationCode verificationCode);
}
