package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.Resident;
import com.usafi.waste_management_system.repository.IResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResidentService {

    @Autowired
    private IResidentRepository residentRepository;

    public String saveResident(Resident resident) {
        Optional<Resident> checkResident =residentRepository.findResidentByEmail(
                resident.getEmail());

        if (checkResident.isPresent()) {
            return "Resident already exists";
        }
        residentRepository.save(resident);
        return "Resident saved";
    }
}
