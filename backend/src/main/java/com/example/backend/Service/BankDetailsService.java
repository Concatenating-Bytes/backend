package com.example.backend.Service;


import com.example.backend.Repo.BankDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BankDetailsService {

    @Autowired
    private BankDetailsRepo bankDetailsRepo;


}
