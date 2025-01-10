package com.usafi.waste_management_system.service;

import com.usafi.waste_management_system.model.CollectionSchedule;

import java.util.List;
import java.util.UUID;

public interface ICollectionScheduleService {

    CollectionSchedule createCollectionSchedule(CollectionSchedule collectionSchedule);
//    collectionScheduleCollectionSchedule updateCollectionSchedule(Date date);
    List<CollectionSchedule> getAllCollectionSchedule();
    void deleteCollectionSchedule(UUID id);
}
