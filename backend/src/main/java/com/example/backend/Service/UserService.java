package com.example.backend.Service;

import com.example.backend.DTO.UserTransactions;
import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Scope("prototype")
public class UserService {

    @Autowired
    private UserRepo repo;

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    //User service class for getting all transactions
    @Transactional
    public List<UserTransactions> getUserTransactions(UUID userId){
        User user = repo.findById(userId).get();

        List<Transactions> allTransactions = new ArrayList<>();

        allTransactions.addAll(user.getRecievedTransactions());
        allTransactions.addAll(user.getSentTransactions());

        List<UserTransactions> dtoObjects = new ArrayList<>();

        for(Transactions tran:allTransactions){
            //Setting Data Transfer Object
            UserTransactions dto = new UserTransactions();
            dto.setSender(user.getId());
            dto.setId(tran.getId());
            dto.setStatus(tran.isStatus());
            dto.setAmount(tran.getAmount());
            dto.setTime(tran.getCreatedAt());
            dto.setReceiver(tran.getReceiver().getId());

            dtoObjects.add(dto);
        }
        return dtoObjects;
    }
}
