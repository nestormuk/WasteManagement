package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.service.CollectionScheduleServiceImpl;
import com.usafi.waste_management_system.service.ComplaintServiceImpl;
import com.usafi.waste_management_system.service.PaymentServiceImpl;
import com.usafi.waste_management_system.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private ComplaintServiceImpl complaintService;
    @Autowired
    private CollectionScheduleServiceImpl collectionScheduleService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardData() {
        try {
            Map<String, Object> dashboardData = new HashMap<>();

            // Get all users
            List<Users> users = userService.findAllUsers();
            dashboardData.put("users", users);
            dashboardData.put("totalUsers", users.size());


            // dashboardData.put("totalPayments", paymentService.getTotalPayments());
            // dashboardData.put("activeComplaints", complaintService.getActiveComplaints());
            // dashboardData.put("upcomingCollections", collectionScheduleService.getUpcomingSchedules());

            return ResponseEntity.ok(dashboardData);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching dashboard data: " + e.getMessage());
        }
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<Users> users = userService.findAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching users: " + e.getMessage());
        }
    }


    @GetMapping("/users/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            Users user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found with email: " + email);
        }
    }

    @PutMapping("/usersUpdate/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable String email, @RequestBody Users updatedUser) {
        try {
            // Directly call the update method
            Users user = userService.updateUserByEmail(email, updatedUser);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found to delete with email: " + email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @DeleteMapping("/usersDelete/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        try {
            String result = userService.deleteUser(email);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}
