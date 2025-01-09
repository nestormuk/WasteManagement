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
public class AuthController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Users user) {
        try {
            Users registeredUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Registration failed: " + ex.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            // Validate user credentials
            Users user = userService.login(credentials.get("email"), credentials.get("password"));

            // Generate JWT Token
            String token = jwtTokenProvider.generateToken(user);

            return ResponseEntity.ok(Map.of("message", "Login successful!", "token", token, "user", user));
        } catch (IllegalStateException | BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "User not found"));
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam int code, @RequestParam String newPassword) {
        userService.resetPassword(code, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> requestData) {
        String email = requestData.get("email");
        String otp = requestData.get("otp");

//        / Log received email and OTP
        System.out.println("Received OTP request for email1: " + email + " with OTP: " + otp);


        boolean isOtpValid = userService.verifyOtp(email, otp);
        if (isOtpValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
    }

}
