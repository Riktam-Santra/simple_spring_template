package com.ricksntra.otp_attendance_backend.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "otp_attendance")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpAttendance {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    UUID id;

    @ManyToOne
    @JoinColumn(name = "platform_user")
    PlatformUser platformUser;

    @ManyToOne
    OtpSession session;
}
