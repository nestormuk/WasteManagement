package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Complaint;

import java.util.List;
import java.util.UUID;

public interface IComplaintService {
    Complaint createComplaint(Complaint complaint);
    List<Complaint> getAllComplaint();
    List<Complaint> getComplaintByUserId(UUID userId);
}
