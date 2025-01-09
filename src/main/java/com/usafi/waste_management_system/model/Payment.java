package com.usafi.waste_management_system.model;

import com.usafi.waste_management_system.util.EPaymentStatus;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    private UUID id=UUID.randomUUID();

    private double amount;

    private Date date;

    @Enumerated(EnumType.STRING)
    private EPaymentStatus status=EPaymentStatus.Unpaid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Payment() {
    }

    public Payment(double amount, Date date, EPaymentStatus status, Users users) {
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EPaymentStatus getStatus() {
        return status;
    }

    public void setStatus(EPaymentStatus status) {
        this.status = status;
    }

    public Users getResident() {
        return users;
    }

    public void setResident(Users users) {
        this.users = users;
    }
}
