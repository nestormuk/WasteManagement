package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Payment;
import com.usafi.waste_management_system.model.Users;
import com.usafi.waste_management_system.repository.IPaymentRepository;
import com.usafi.waste_management_system.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements IPaymentService{

    @Autowired
    private IPaymentRepository paymentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Payment createPayment(Payment payment) {
        Optional<Users> getUser = userRepository.findByEmail(payment.getUsers().getEmail());
        if (getUser.isEmpty()) {
            return null;
        }
        payment.setUsers(getUser.get());

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

    @Override
    public Payment findByUsersAndDate(String userEmail, String date) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            return null;
        }

        try {
            // Convert String to java.util.Date
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return paymentRepository.findByUsersAndDate(user.get(), parsedDate);
        } catch (ParseException e) {
            // Handle invalid date format
            throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'.", e);
        }
    }


    @Override
    public String deletePaymentByUsersAndDate(String userEmail, Date date) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            return "User not found";
        }
        paymentRepository.deletePaymentByUsersAndDate(user.get(), date);
        Payment payment = paymentRepository.findByUsersAndDate(user.get(), date);
        return (payment == null) ? "Payment deleted successfully" : "Error deleting payment";
    }

    public String updatePaymentByUsersAndDate(String userEmail, Date date, Payment payment) {
        Optional<Users> user = userRepository.findByEmail(userEmail);
        if (user.isEmpty()) {
            return "User not found";
        }

        Payment existingPayment = paymentRepository.findByUsersAndDate(user.get(), date);
        if (existingPayment != null) {
            existingPayment.setAmount(payment.getAmount());
            existingPayment.setStatus(payment.getStatus());
            paymentRepository.save(existingPayment);
            return "Payment updated successfully";
        }
        return "Error updating payment";
    }

}
