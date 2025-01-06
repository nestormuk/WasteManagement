package com.usafi.waste_management_system.repository;

import com.usafi.waste_management_system.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IResidentRepository extends JpaRepository<Resident, UUID> {

    Optional<Resident> findResidentByEmail(String email);
}
