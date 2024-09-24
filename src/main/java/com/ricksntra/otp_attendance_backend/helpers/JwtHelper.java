package com.ricksntra.otp_attendance_backend.helpers;

import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import com.ricksntra.otp_attendance_backend.repositories.PlatformUserRepository;
import com.ricksntra.otp_attendance_backend.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JwtHelper {
    private final PlatformUserRepository platformUserRepository;
    JwtService jwtService;

    public PlatformUser getUserFromJwt(String token) {
        String tempToken = token;
        if (token.startsWith("Bearer ")) {
            tempToken = token.substring(7);
        }

        PlatformUser user = platformUserRepository.findByUsername(jwtService.extractName(tempToken));
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        return user;
    }
}
