package com.ricksntra.otp_attendance_backend.service.serviceimpl;

import com.ricksntra.otp_attendance_backend.repositories.PlatformUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserDetailsService {
    PlatformUserRepository platformUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return platformUserRepository.findByUsername(username);
    }
}
