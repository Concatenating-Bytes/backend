package com.example.backend.Controller;

import com.example.backend.DTO.UserBankInfo;
import com.example.backend.Entity.UserBankDetails;
import com.example.backend.Repo.BankDetailsRepo;
import com.example.backend.Repo.BankRepo;
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
    private final BankDetailsRepo bankRepo;

    // ✅ Add bank account to user
    @PostMapping("/add/{userId}")
    public UserBankInfo addBankAccount(
            @PathVariable UUID userId,
            @RequestBody UserBankInfo userBankInfo) {

        return bankDetailsService.addBankAccount(userId, userBankInfo);
    }

    // ✅ Get all bank accounts for a user
    @GetMapping("/user/{userId}")
    public List<UserBankDetails> getUserBankAccounts(@PathVariable UUID userId) {
        return bankDetailsService.getUserBankAccounts(userId);
    }

    // ✅ Get all bank accounts in system
    @GetMapping("/all")
    public List<UserBankDetails> getAllAccounts() {
        return bankDetailsService.getAllAccounts();
    }

    // ✅ Get user balance
    @GetMapping("/balance/{userId}")
    public Float getBalance(@PathVariable UUID userId) {
        return bankRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"))
                .getBalance();
    }

    // ✅ Delete bank account
    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        bankDetailsService.deleteAccount(id);
        return "Bank account deleted successfully";
    }
}
