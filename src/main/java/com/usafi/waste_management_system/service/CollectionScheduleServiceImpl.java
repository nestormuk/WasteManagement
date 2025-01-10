package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.CollectionSchedule;
import com.usafi.waste_management_system.repository.ICollectionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CollectionScheduleServiceImpl implements ICollectionScheduleService {

    @Autowired
    private ICollectionScheduleRepository collectionScheduleRepository;

    @Override
    public CollectionSchedule createCollectionSchedule(CollectionSchedule collectionSchedule) {
        return collectionScheduleRepository.save(collectionSchedule);
    }


    @Override
    public List<CollectionSchedule> getAllCollectionSchedule() {
        return collectionScheduleRepository.findAll();
    }

    @Override
    public void deleteCollectionSchedule(UUID id) {

    }
}
