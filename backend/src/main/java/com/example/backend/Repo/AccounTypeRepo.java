package com.example.backend.Repo;

import com.example.backend.Entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccounTypeRepo extends JpaRepository<AccountType,Long> {
}
