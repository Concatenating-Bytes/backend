package com.example.backend.Controller;
import com.example.backend.DTO.UserBankInfo;
import com.example.backend.DTO.UserTransactions;
import com.example.backend.Service.BankDetailsService;
import com.example.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    private UserService service;

    @Autowired
    private BankDetailsService bankDetailsService;

    @GetMapping("users/userAllTransactions/{userId}")
    public List<UserTransactions> getUserTransactions(@PathVariable("userId")UUID userId){
        List<UserTransactions> transactions = service.getUserTransactions(userId);
        return transactions;
    }

    @GetMapping("users/userBankDetails/{user_id}")
    public List<UserBankInfo> getUserBankDetails(@PathVariable("user_id")UUID user_id){
        List<UserBankInfo> accounts = service.getAccounts(user_id);
        return accounts;
    }
}
