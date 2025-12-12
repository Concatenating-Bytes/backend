package com.example.backend.Repo;

import com.example.backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNo(String phone_no);
    Optional<User> findByPhoneNo(String phoneNo);
}
