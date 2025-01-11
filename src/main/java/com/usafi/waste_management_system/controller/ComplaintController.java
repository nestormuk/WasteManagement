package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.Complaint;
import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.repository.IUserRepository;
import com.usafi.waste_management_system.service.ComplaintServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/submit")
    public ResponseEntity<?> submitComplaint(@RequestBody Complaint complaint ) {
        Users user = getCurrentUser();
        complaint.setUsers(user);

        Complaint complaints = complaintService.createComplaint(complaint);
        return ResponseEntity.ok(Map.of(
                "message",
                "Complaint submitted successfully",
                "complaint", complaints));
    }
}
