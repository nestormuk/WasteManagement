package com.usafi.waste_management_system.repository;

import com.usafi.waste_management_system.model.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IComplaintRepository extends JpaRepository<Complaint, UUID> {

    List<Complaint> findByUsersId(UUID userId);
}
