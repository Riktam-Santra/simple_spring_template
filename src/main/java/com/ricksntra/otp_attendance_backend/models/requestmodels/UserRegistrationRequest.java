package com.ricksntra.otp_attendance_backend.models.requestmodels;

import lombok.Getter;

@Getter
public class UserRegistrationRequest {
    String username;
    String originalName;
    String email;
    String password;
}
