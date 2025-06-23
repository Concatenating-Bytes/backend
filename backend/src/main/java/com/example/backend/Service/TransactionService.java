package com.example.backend.Service;

import com.example.backend.Entity.Transactions;
import com.example.backend.Repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class TransactionService {

    @Autowired
    private TransactionRepo repo;

    public List<Transactions> getAllTransactions(){
        return repo.findAll();
    }

}
