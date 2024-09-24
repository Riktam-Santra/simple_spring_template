package com.ricksntra.otp_attendance_backend.models.requestmodels;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttendOtpSessionRequest {
    String sessionOtp;
}
