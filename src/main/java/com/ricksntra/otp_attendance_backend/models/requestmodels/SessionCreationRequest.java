package com.ricksntra.otp_attendance_backend.models.requestmodels;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class SessionCreationRequest {
    String sessionName;
    Timestamp sessionStart;
    Timestamp sessionExpiry;
}
