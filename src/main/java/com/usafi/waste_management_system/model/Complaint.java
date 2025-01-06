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
    @JoinColumn(name = "resident_id")
    private Resident resident;

    public Complaint() {
    }

    public Complaint(String title, String description, EComplaintStatus status, Date date, Resident resident) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.date = date;
        this.resident = resident;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EComplaintStatus getStatus() {
        return status;
    }

    public void setStatus(EComplaintStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }
}
