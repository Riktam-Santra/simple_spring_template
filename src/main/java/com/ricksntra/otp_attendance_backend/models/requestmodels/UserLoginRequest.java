package com.ricksntra.otp_attendance_backend.models.requestmodels;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    String username;
    String password;
}
