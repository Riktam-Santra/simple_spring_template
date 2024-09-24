package com.ricksntra.otp_attendance_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "otp_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OtpSession {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    UUID id;

    @Column(name = "session_name", nullable = false)
    String sessionName;

    @Column(name = "session_start", nullable = false)
    Timestamp sessionStart;

    @Column(name = "session_expiry", nullable = false)
    Timestamp sessionExpiry;

    @Column(name = "session_complete")
    @ColumnDefault("false")
    Boolean sessionComplete;

    @Column(name = "session_otp", nullable = false)
    String sessionOtp;

    @ManyToOne
    PlatformUser owner;
}
