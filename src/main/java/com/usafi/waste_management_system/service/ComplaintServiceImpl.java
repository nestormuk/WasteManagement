package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Complaint;
import com.usafi.waste_management_system.repository.IComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComplaintServiceImpl implements IComplaintService {

    @Autowired
    private IComplaintRepository complaintRepository;

    @Override
    public Complaint createComplaint(Complaint complaint) {
        return complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> getAllComplaint() {
        return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> getComplaintByUserId(UUID userId) {
        return complaintRepository.findByUsersId(userId);
    }
}
