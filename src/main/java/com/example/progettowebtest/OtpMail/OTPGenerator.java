package com.example.progettowebtest.OtpMail;

import java.security.SecureRandom;

public class OTPGenerator {
    public static String generateOTP() {
        int length = 5;

        String charset = "0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charset.length());
            otp.append(charset.charAt(index));
        }

        return otp.toString();
    }

}
