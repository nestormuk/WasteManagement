package com.usafi.waste_management_system.model;

import com.usafi.waste_management_system.util.EAccountStatus;
import com.usafi.waste_management_system.util.EPaymentStatus;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Resident {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private EAccountStatus accountStatus=EAccountStatus.PENDING;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "resident", cascade = CascadeType.ALL)
    private List<Payment> payments;

    public Resident() {
    }

    public Resident(String name, String location, String email, String password, EAccountStatus accountStatus, List<Complaint> complaints, List<Payment> payments) {
        this.name = name;
        this.location = location;
        this.email = email;
        this.password = password;
        this.accountStatus = accountStatus;
        this.complaints = complaints;
        this.payments = payments;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EAccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(EAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<Complaint> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
