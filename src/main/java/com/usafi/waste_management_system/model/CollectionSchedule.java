package com.usafi.waste_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class CollectionSchedule {
    @Id
    private UUID id;

    private Date scheduleDate;

    public CollectionSchedule() {
    }

    public CollectionSchedule(Date scheduleDate, UUID id) {
        this.scheduleDate = scheduleDate;
        this.id = id;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
}
