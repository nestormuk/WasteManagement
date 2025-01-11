package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.Complaint;
import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.repository.IUserRepository;
import com.usafi.waste_management_system.service.ComplaintServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/complaint")
public class ComplaintController {

    @Autowired
    private ComplaintServiceImpl complaintService;

    @Autowired
    private IUserRepository usersRepository;

    private Users getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<?> submitComplaint(@RequestBody @Valid Complaint complaint) {
        try {
            Users user = getCurrentUser();
            complaint.setUsers(user);
            complaint.setDate(new java.util.Date());

            Complaint savedComplaint = complaintService.createComplaint(complaint);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of(
                            "message", "Complaint submitted successfully",
                            "complaint", savedComplaint
                    ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Failed to submit complaint: " + e.getMessage()));
        }
    }
}
