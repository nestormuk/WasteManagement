package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Payment;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IPaymentService {

    Payment createPayment(Payment payment);
    List<Payment> getAllPayment();
    List<Payment> getPaymentByUserId(UUID userId);

    Payment findByUsersAndDate(String userId, String date);
    String deletePaymentByUsersAndDate(String userId, Date date);
}
