package com.ricksntra.otp_attendance_backend.controllers.v1;

import com.ricksntra.otp_attendance_backend.entities.OtpAttendance;
import com.ricksntra.otp_attendance_backend.entities.OtpSession;
import com.ricksntra.otp_attendance_backend.entities.PlatformUser;
import com.ricksntra.otp_attendance_backend.helpers.JwtHelper;
import com.ricksntra.otp_attendance_backend.models.dto.SessionInfoDTO;
import com.ricksntra.otp_attendance_backend.models.requestmodels.AttendOtpSessionRequest;
import com.ricksntra.otp_attendance_backend.models.requestmodels.SessionCreationRequest;
import com.ricksntra.otp_attendance_backend.repositories.OtpAttendanceRepository;
import com.ricksntra.otp_attendance_backend.repositories.OtpSessionRepository;
import com.ricksntra.otp_attendance_backend.util.OTPUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionController {
    ModelMapper modelMapper;
    JwtHelper jwtHelper;
    private final OtpSessionRepository otpSessionRepository;
    private final OtpAttendanceRepository otpAttendanceRepository;

    @PostMapping("/create")
    ResponseEntity<?> createSession(@RequestHeader("Authorization") String authToken,@RequestBody SessionCreationRequest request) {
        PlatformUser user = jwtHelper.getUserFromJwt(authToken);

        if (request.getSessionStart().before(new Timestamp(System.currentTimeMillis()))) {
            return ResponseEntity.badRequest().body("Session time must me right now or in the future.");
        }

        if (request.getSessionExpiry().before(new Timestamp(System.currentTimeMillis()))) {
            return ResponseEntity.badRequest().body("The expiry time has already passed");
        }

        if (request.getSessionExpiry().before(request.getSessionStart())) {
            return ResponseEntity.badRequest().body("Your clock is running backwards");
        }

        OtpSession session = OtpSession.builder()
                .sessionName(request.getSessionName())
                .sessionStart(request.getSessionStart())
                .sessionExpiry(request.getSessionExpiry())
                .sessionOtp(OTPUtil.generateOTP())
                .sessionComplete(false)
                .owner(user)
                .build();

        session = otpSessionRepository.save(session);

        SessionInfoDTO dto = modelMapper.map(session, SessionInfoDTO.class);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSession(@RequestHeader("Authorization") String authToken, @PathVariable UUID id) {
        PlatformUser user = jwtHelper.getUserFromJwt(authToken);
        return otpSessionRepository.findById(id).map(
                (x) -> {
                    if (x.getOwner() == user) {
                        otpSessionRepository.delete(x);
                        return ResponseEntity.ok().body("Session has been deleted");
                    } else {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not the session owner");
                    }
                }
        ).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/owned")
    ResponseEntity<?> getUserSessions(@RequestHeader("Authorization") String authToken) {
        PlatformUser user = jwtHelper.getUserFromJwt(authToken);
        List<OtpSession> userOwnedSessions = otpSessionRepository.findOtpSessionsByOwner(user);

        return ResponseEntity.ok(userOwnedSessions);
    }

    @GetMapping("/user/attended")
    ResponseEntity<?> getUserAttendedSessions(@RequestHeader("Authorization") String authToken) {
        PlatformUser user = jwtHelper.getUserFromJwt(authToken);
        List<OtpAttendance> attendedSessions = otpAttendanceRepository.findOtpAttendancesByPlatformUser(user);

        return ResponseEntity.ok(attendedSessions);
    }

    @PostMapping("/attend/{sessionId}")
    ResponseEntity<?> addUserAttendance(
            @RequestHeader("Authorization") String authToken,
            @RequestBody AttendOtpSessionRequest request,
            @PathVariable UUID sessionId) {
        PlatformUser user = jwtHelper.getUserFromJwt(authToken);
        Optional<OtpSession> attendingSession = otpSessionRepository.findById(sessionId);
        
        // session not found
        if (attendingSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        OtpSession session = attendingSession.get();

        // session has completed
        if (session.getSessionComplete()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session has been completed");
        }

        // session has not started
        if (session.getSessionStart().before(new Timestamp(System.currentTimeMillis()))) {
            return ResponseEntity.badRequest().body("Sessions has not started yet");
        }

        // session already attended
        if (otpAttendanceRepository.findOtpAttendanceByPlatformUserAndId(user, sessionId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Session already attended");
        }

        //session expired but not updated in db
        if (session.getSessionExpiry().after(new Timestamp(System.currentTimeMillis()))) {
            session.setSessionComplete(true);
            otpSessionRepository.save(session);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Session has expired");
        }

        OtpAttendance attendance = OtpAttendance.builder().platformUser(user).session(attendingSession.get()).build();
        otpAttendanceRepository.save(attendance);

        return ResponseEntity.ok().build();
    }
}
