package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.repository.IUserRepository;
import com.usafi.waste_management_system.util.EAccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements IUserService {

    private static final Map<String, String> otpStore = new HashMap<>();
    private static final Map<String, Long> otpTimestamp = new HashMap<>();

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Use @Lazy to avoid circular dependency
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Users registerUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String otp = generateOtp(user.getEmail());
        user.setVerificationCode(Integer.parseInt(otp));
        user.setVerificationExp(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        user.setAccountStatus(EAccountStatus.PENDING);
        userRepository.save(user);

        sendVerificationEmail(user);
        return user;
    }

    @Override
    public Users login(String email, String password) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        switch (user.getAccountStatus()) {
            case PENDING:
                throw new IllegalStateException("Account is pending verification. Please check your email.");
            case REJECTED:
                throw new IllegalStateException("Account has been rejected. Contact support for assistance.");
            case SUSPENDED:
                throw new IllegalStateException("Account has been suspended. Contact support for assistance.");
            case APPROVED:
                break;
            default:
                throw new IllegalStateException("Unknown account status. Please contact support.");
        }

        return user;
    }

    @Override
    public void forgotPassword(String email) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String otp = generateOtp(email);
        user.setVerificationCode(Integer.parseInt(otp));
        user.setVerificationExp(new Date(System.currentTimeMillis() + 15 * 60 * 1000));
        userRepository.save(user);

        sendResetPasswordEmail(user);
    }

    @Override
    public void resetPassword(int verificationCode, String newPassword) {
        Users user = userRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification code"));

        if (user.getVerificationExp().before(new Date())) {
            throw new IllegalStateException("Verification code expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationCode(0);
        user.setVerificationExp(null);
        userRepository.save(user);
    }

    @Override
    public String generateOtp(String email) {
        String otp = generateRandomOtp();
        otpStore.put(email, otp);
        otpTimestamp.put(email, System.currentTimeMillis());
        return otp;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        Long storedTime = otpTimestamp.get(email);

        if (storedOtp != null && storedTime != null) {
            long currentTime = System.currentTimeMillis();
            long expirationTime = storedTime + TimeUnit.MINUTES.toMillis(5);

            if (currentTime <= expirationTime && storedOtp.equals(otp)) {
                otpStore.remove(email);
                otpTimestamp.remove(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Users> loadUserByUsername(String email) {
        return userRepository.findByEmail(email);
    }


    private void sendVerificationEmail(Users user) {
        String subject = "Verify Your Account";
        String message = "Your verification code is " + user.getVerificationCode();
        sendEmail(user.getEmail(), subject, message);
    }

    private void sendResetPasswordEmail(Users user) {
        String subject = "Reset Your Password";
        String message = "Your reset code is " + user.getVerificationCode();
        sendEmail(user.getEmail(), subject, message);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    private String generateRandomOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
