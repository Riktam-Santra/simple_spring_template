package com.ricksntra.otp_attendance_backend.repositories;

import com.ricksntra.otp_attendance_backend.entities.OtpSession;
import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OtpSessionRepository extends JpaRepository<OtpSession, UUID> {
    List<OtpSession> findOtpSessionsByOwner(PlatformUser owner);
}
