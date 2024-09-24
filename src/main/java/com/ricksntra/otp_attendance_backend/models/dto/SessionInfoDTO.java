package com.ricksntra.otp_attendance_backend.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class SessionInfoDTO {
    UUID id;
    String sessionName;
    Timestamp sessionStart;
    Timestamp sessionExpiry;
    Boolean sessionComplete;
    String sessionOtp;
}
