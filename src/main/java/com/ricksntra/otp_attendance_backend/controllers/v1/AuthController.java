package com.ricksntra.otp_attendance_backend.controllers.v1;

import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import com.ricksntra.otp_attendance_backend.models.requestmodels.UserLoginRequest;
import com.ricksntra.otp_attendance_backend.models.requestmodels.UserRegistrationRequest;
import com.ricksntra.otp_attendance_backend.models.responsemodels.UserLoginResponse;
import com.ricksntra.otp_attendance_backend.repositories.PlatformUserRepository;
import com.ricksntra.otp_attendance_backend.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    PlatformUserRepository platformUserRepository;
    PasswordEncoder passwordEncoder;
    JwtService jwtService;

    @PostMapping("/signup")
    ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest request) {
        PlatformUser platformUser = platformUserRepository.findByUsername(request.getUsername());

        if (platformUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }

        platformUser = PlatformUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userEmail(request.getEmail())
                .originalName(request.getOriginalName())
                .build();
        platformUserRepository.save(platformUser);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
        PlatformUser platformUser = platformUserRepository.findByUsername(request.getUsername());
        if (platformUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not found");
        }
        if (!passwordEncoder.matches(request.getPassword(), platformUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }

        String token = jwtService.generateToken(platformUser);

        return ResponseEntity.ok(UserLoginResponse.builder().token(token).build());
    }
}
