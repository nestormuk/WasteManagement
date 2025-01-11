package com.usafi.waste_management_system.repository;

import com.usafi.waste_management_system.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);
    Optional<Users> findByVerificationCode(int code);
    String deleteByEmail(String email);
}
