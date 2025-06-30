package com.example.backend.Repo;

import com.example.backend.Entity.UserBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankDetailsRepo extends JpaRepository<UserBankDetails,Long> {

}
