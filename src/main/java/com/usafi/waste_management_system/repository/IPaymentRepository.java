package com.usafi.waste_management_system.repository;

import com.usafi.waste_management_system.model.Payment;
import com.usafi.waste_management_system.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByUsersId(UUID userId);

    void deletePaymentByUsersAndDate(Users user, Date date);

    Payment findByUsersAndDate(Users user, Date date);
}

