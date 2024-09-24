package com.ricksntra.otp_attendance_backend.repositories;

import com.ricksntra.otp_attendance_backend.entities.OtpAttendance;
import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OtpAttendanceRepository extends JpaRepository<OtpAttendance, UUID> {
    List<OtpAttendance> findOtpAttendancesByPlatformUser(PlatformUser platformUser);
    Optional<OtpAttendance> findOtpAttendanceByPlatformUserAndId(PlatformUser platformUser, UUID id);
}
