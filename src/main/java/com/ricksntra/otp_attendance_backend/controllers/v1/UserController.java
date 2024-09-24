package com.ricksntra.otp_attendance_backend.controllers.v1;

import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import com.ricksntra.otp_attendance_backend.helpers.JwtHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final JwtHelper jwtHelper;

    public UserController(JwtHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @GetMapping("/me")
    public ResponseEntity<PlatformUser> getUserDetails(@RequestHeader("Authorization") String token) {
        PlatformUser user = jwtHelper.getUserFromJwt(token);
        return ResponseEntity.ok(user);
    }
}
