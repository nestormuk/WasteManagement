package com.usafi.waste_management_system.controller;

import com.usafi.waste_management_system.model.Resident;
import com.usafi.waste_management_system.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/resident")
public class ResidentController {

    @Autowired
    private ResidentService residentService;

    @PostMapping(value = "/saveResident" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveResident(@RequestBody Resident resident) {

        String saveResident = residentService.saveResident(resident);

        if (saveResident.equalsIgnoreCase("Resident already exists")){
            return new ResponseEntity<>(saveResident, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(saveResident, HttpStatus.CREATED);

    }
}
