package com.example.backend.Controller;

import com.example.backend.DTO.UserBankInfo;
import com.example.backend.Entity.UserBankDetails;
import com.example.backend.Service.BankDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
public class BankDetailsController {
    private final BankDetailsService bankDetailsService;


    //add bank A/c to user
    @PostMapping("/add/{userId}")
    public UserBankInfo addBankAccount(@PathVariable String userId, @RequestBody UserBankInfo userBankInfo) {
        UUID uid=UUID.fromString(userId);
        return bankDetailsService.addBankAccount(uid, userBankInfo);
    }

    //get user bank A/c s
    @GetMapping("/user/{userId}")
    public List<UserBankDetails> getUserBankAccounts(@PathVariable UUID userId) {
        return bankDetailsService.getUserBankAccounts(userId);
    }

    //get all bank A/c s
    @GetMapping("/all")
    public List<UserBankDetails> getAllAccounts() {
        return bankDetailsService.getAllAccounts();
    }

    //delete bank A/c
    @DeleteMapping("/{id}")
    public String getAllAccounts(@PathVariable Long id) {
        bankDetailsService.deleteAccount(id);
        return "Bank account deleted successfully";

    }
}