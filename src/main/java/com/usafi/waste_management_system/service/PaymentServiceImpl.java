package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Payment;
import com.usafi.waste_management_system.repository.IPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements IPaymentService{

    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> getPaymentByUserId(UUID userId) {
        return paymentRepository.findByUsersId(userId);
    }
}
