package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.Payment;
import com.usafi.waste_management_system.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentServiceImpl paymentService;


    @PostMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        Payment createdPayment = paymentService.createPayment(payment);
        if (createdPayment == null) {
            return ResponseEntity.badRequest().body("Invalid user email. User does not exist.");
        }
        return ResponseEntity.ok(createdPayment);
    }


    @GetMapping("/getAllPayments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayment();
        return ResponseEntity.ok(payments);
    }


    @GetMapping("/getPaymentByUser/{userId}")
    public ResponseEntity<List<Payment>> getPaymentsByUserId(@PathVariable UUID userId) {
        List<Payment> payments = paymentService.getPaymentByUserId(userId);
        return ResponseEntity.ok(payments);
    }


    @GetMapping("/paymentByUserDate/{userEmail}/date/{date}")
    public ResponseEntity<Payment> getPaymentByUserAndDate(
            @PathVariable String userEmail,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Payment payment = paymentService.findByUsersAndDate(userEmail, new SimpleDateFormat("yyyy-MM-dd").format(date));
        if (payment != null) {
            return ResponseEntity.ok(payment);
        }
        return ResponseEntity.notFound().build();
    }



    @DeleteMapping("/DeleteUser/{userId}/date/{date}")
    public ResponseEntity<String> deletePaymentByUserAndDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        String result = paymentService.deletePaymentByUsersAndDate(userId, date);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/UpdateUser/{userId}/date/{date}")
    public ResponseEntity<String> updatePaymentByUserAndDate(
            @PathVariable String userId,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestBody Payment payment) {
        String result = paymentService.updatePaymentByUsersAndDate(userId, date, payment);
        return ResponseEntity.ok(result);
    }
}
