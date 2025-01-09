package com.usafi.waste_management_system.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {
    public void sendEmail(String to, String subject, String body) {
        // Use JavaMailSender or any email library to send an email
        System.out.printf("Email sent to %s: %s - %s%n", to, subject, body);
    }
}