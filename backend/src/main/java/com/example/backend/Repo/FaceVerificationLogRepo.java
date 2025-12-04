package com.example.backend.Repo;

import com.example.backend.Entity.FaceVerificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FaceVerificationLogRepo extends JpaRepository<FaceVerificationLog, UUID> {
}
