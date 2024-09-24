package com.ricksntra.otp_attendance_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "platform_users")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUser implements UserDetails {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    UUID id;

    @Column(name = "user_name" , nullable = false)
    String username;

    @Column(name = "original_name")
    String originalName;

    @Column(name = "user_email", nullable = false)
    String userEmail;

    @Column(nullable = false)
    String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
