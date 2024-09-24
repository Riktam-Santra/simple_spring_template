package com.ricksntra.otp_attendance_backend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
