package com.example.backend.Service;

import com.example.backend.Entity.AccountType;
import com.example.backend.Repo.AccountTypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTypeService {
    private final AccountTypeRepo accountTypeRepo;

    //create account type
    public AccountType create(AccountType accountType){
        return accountTypeRepo.save(accountType);
    }

    //get all account type
    public List<AccountType> getAllAccountType (){
        return accountTypeRepo.findAll();
    }

    //get by account id
    public AccountType getById(Long id){
        return accountTypeRepo.findById(id).orElseThrow(()->new RuntimeException("Account type not found"));
    }

    //delete
    public void delete(Long id){
        accountTypeRepo.deleteById(id);
    }

}
