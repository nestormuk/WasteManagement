package com.usafi.waste_management_system.controller;


import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.security.JwtTokenProvider;
import com.usafi.waste_management_system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
public class AuthController {

    private final IUserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(IUserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            Users registeredUser = userService.registerUser(user);
            String token = jwtTokenProvider.generateToken(registeredUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "message", "Registration successful!",
                    "token", token,
                    "user", registeredUser
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Registration failed: " + ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            Users user = userService.login(credentials.get("email"), credentials.get("password"));
            String token = jwtTokenProvider.generateToken(user);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful!",
                    "token", token,
                    "user", user
            ));
        } catch (IllegalStateException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", ex.getMessage()));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam int code, @RequestParam String newPassword) {
        userService.resetPassword(code, newPassword);
        return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String otp = requestData.get("otp");

        System.out.println("Received OTP request for email: " + email + " with OTP: " + otp);

        boolean isOtpValid = userService.verifyOtp(email, otp);
        if (isOtpValid) {
            return ResponseEntity.ok(Map.of("message", "OTP verified successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid OTP"));
        }
    }
}