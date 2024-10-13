package com.crypto.CoinFolio.Utils;

import java.util.Random;

public class OtpUtils {
    public static String generateOtp(){
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for(int i=0;i<otpLength;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
