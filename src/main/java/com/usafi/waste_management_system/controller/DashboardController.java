package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.CollectionSchedule;
import com.usafi.waste_management_system.model.Complaint;
import com.usafi.waste_management_system.model.Payment;
import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.repository.IUserRepository;

import com.usafi.waste_management_system.service.CollectionScheduleServiceImpl;
import com.usafi.waste_management_system.service.ComplaintServiceImpl;
import com.usafi.waste_management_system.service.PaymentServiceImpl;
import com.usafi.waste_management_system.util.EAccountStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
public class DashboardController {

    @Autowired
    private IUserRepository usersRepository;

    private Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getDashboardData() {
        Users user = getCurrentUser();
        return ResponseEntity.ok(Map.of("message", "Dashboard data retrieved successfully", "user", user));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminDashboard() {
        Users user = getCurrentUser();

        if (user.getAccountStatus() != EAccountStatus.APPROVED) {
            return ResponseEntity.status(403).body(Map.of("message", "Your account is not approved yet."));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Admin dashboard data retrieved successfully",
                "user", user
        ));
    }
    @Autowired
    private PaymentServiceImpl paymentServiceImpl;

    @Autowired
    private CollectionScheduleServiceImpl collectionScheduleServiceImpl;

    @Autowired
    private ComplaintServiceImpl complaintService;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserDashboard() {
        Users user = getCurrentUser();

        if (user.getAccountStatus() == EAccountStatus.PENDING) {
            return ResponseEntity.status(403).body(Map.of(
                    "message", "Your account is pending verification. Please wait for approval."
            ));
        }

        // Fetch data using services
        List<Payment> payments = paymentServiceImpl.getPaymentByUserId(user.getId());
        List<Complaint> complaints = complaintService.getComplaintByUserId(user.getId());
        List<CollectionSchedule> collectionSchedules = collectionScheduleServiceImpl.getAllCollectionSchedule();

        return ResponseEntity.ok(Map.of(
                "message", "User dashboard data retrieved successfully",
                "user", user,
                "payments", payments != null ? payments : List.of(),
                "complaints", complaints != null ? complaints : List.of(),
                "collectionSchedules", collectionSchedules != null ? collectionSchedules : List.of()
        ));
    }



}