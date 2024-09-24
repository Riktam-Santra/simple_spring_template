package com.ricksntra.otp_attendance_backend.repositories;

import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlatformUserRepository extends JpaRepository<PlatformUser, UUID> {
    PlatformUser findByUsername(String username);
}
