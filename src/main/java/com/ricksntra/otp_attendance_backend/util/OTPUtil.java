package com.ricksntra.otp_attendance_backend.util;

import java.security.SecureRandom;

public class OTPUtil {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_LENGTH = 6;

    public static String generateOTP() {
        int otp = secureRandom.nextInt(900000) + 100000;
        return String.valueOf(otp);
    }
}
