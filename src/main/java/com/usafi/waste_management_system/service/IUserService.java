package com.usafi.waste_management_system.service;


import com.usafi.waste_management_system.model.Users;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Users registerUser(Users user);
    Users login(String email, String password);
    void forgotPassword(String email);
    void resetPassword(int verificationCode, String newPassword);
    String generateOtp(String email);  // Generate OTP for email
    boolean verifyOtp(String email, String otp);

   Users loadUserByUsername(String email);
   Users findUserByEmail(String email);
   List<Users> findAllUsers();
   String deleteUser(String email);
}
