package com.ricksntra.spring_jwt_jpa_template.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
