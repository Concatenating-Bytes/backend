package com.example.backend.Controller;
import com.example.backend.DTO.UserTransactions;
import com.example.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    private UserService service;

    @GetMapping("users/userAllTransactions/{userId}")
    public List<UserTransactions> getUserTransactions(@PathVariable("userId")UUID userId){
        List<UserTransactions> transactions = service.getUserTransactions(userId);
        return transactions;
    }
}
