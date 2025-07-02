package com.example.backend.Service;

import com.example.backend.DTO.UserBankInfo;
import com.example.backend.DTO.UserTransactions;
import com.example.backend.Entity.Transactions;
import com.example.backend.Entity.User;
import com.example.backend.Entity.UserBankDetails;
import com.example.backend.Repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Scope("prototype")
@Transactional
public class UserService {

    @Autowired
    private UserRepo repo;

    public List<User> getAllUsers(){
        return repo.findAll();
    }

    //User service class for getting all transactions
    public List<UserTransactions> getUserTransactions(UUID userId){
        User user = repo.findById(userId).orElseThrow(()-> new EntityNotFoundException("No transactions exist for id:"+userId));

        List<Transactions> allTransactions = new ArrayList<>();

        allTransactions.addAll(user.getReceivedTransactions());
        allTransactions.addAll(user.getSentTransactions());

        List<UserTransactions> dtoObjects = new ArrayList<>();

        for(Transactions tran:allTransactions){
            //Setting Data Transfer Object
            UserTransactions dto = new UserTransactions();
            dto.setSender(user.getId());
            dto.setTransactionId(tran.getId());
            dto.setStatus(tran.isStatus());
            dto.setAmount(tran.getAmount());
            dto.setTime(tran.getCreatedAt());
            dto.setReceiver(tran.getReceiver().getId());

            dtoObjects.add(dto);
        }
        return dtoObjects;
    }

    //User bank accounts
    public List<UserBankInfo> getAccounts(UUID user_id){
        List<UserBankDetails> accounts = new ArrayList<>();

        User user = repo.findById(user_id).orElseThrow(()->new EntityNotFoundException("Account not found with id:-"+user_id));
        accounts.addAll(user.getBankDetails());

        List<UserBankInfo> dtoObjects = new ArrayList<>();

        for(UserBankDetails info:accounts){
            UserBankInfo dto = new UserBankInfo();

            dto.setId(info.getId());
            dto.setUser_id(user_id);
            dto.setAccount_type(info.getType().getType_name());
            dto.setAccount_number(info.getAccount_number());
            dto.setBalance(info.getBalance());
            dto.setIfsc_code(info.getBank_ifsc().getIfsc_code());
            dto.setCreated_at(info.getCreated_at());
            dto.setAccount_holder_name(info.getAccount_holder_name());

            dtoObjects.add(dto);
        }
        return dtoObjects;

    }
}
