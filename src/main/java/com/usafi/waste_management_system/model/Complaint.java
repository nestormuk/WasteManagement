package com.usafi.waste_management_system.model;

import com.usafi.waste_management_system.util.EComplaintStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;
import java.util.UUID;

@Entity
public class Complaint {
    @Id
    private UUID id=UUID.randomUUID();

    private String title;

    private String description;

    private EComplaintStatus status;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    public Complaint() {
    }


}
