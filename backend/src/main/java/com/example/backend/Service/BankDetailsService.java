package com.example.backend.Service;

import com.example.backend.DTO.UserBankInfo;
import com.example.backend.Entity.AccountType;
import com.example.backend.Entity.Bank;
import com.example.backend.Entity.User;
import com.example.backend.Entity.UserBankDetails;
import com.example.backend.Mapper.UserBankMapper;
import com.example.backend.Repo.AccountTypeRepo;
import com.example.backend.Repo.BankDetailsRepo;
import com.example.backend.Repo.BankRepo;
import com.example.backend.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class BankDetailsService {

    private final BankDetailsRepo bankDetailsRepo;
    private final UserRepo userRepo;
    private final BankRepo bankRepo;
    private final AccountTypeRepo accountTypeRepo;

    public UserBankInfo addBankAccount(UUID userId, UserBankInfo dto) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Bank bank = bankRepo.findByIfscCode(dto.getIfscCode())
                .orElseThrow(() -> new RuntimeException("Bank not found"));
        
        // AccountType is optional - only fetch if typeId is provided
        AccountType type = null;
        if (dto.getTypeId() != null) {
            type = accountTypeRepo.findById(dto.getTypeId())
                    .orElse(null);
        }

        UserBankDetails bd = UserBankMapper.toEntity(dto, user, bank, type);

        UserBankDetails saved = bankDetailsRepo.save(bd);

        if (user.getBankDetails() == null) {
            user.setBankDetails(new ArrayList<>());
        }
        user.getBankDetails().add(saved);
        userRepo.save(user);

        return UserBankMapper.toDto(saved);
    }

    public List<UserBankDetails> getUserBankAccounts(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBankDetails();
    }

    public List<UserBankDetails> getAllAccounts() {
        return bankDetailsRepo.findAll();
    }

    public void deleteAccount(Long id) {
        bankDetailsRepo.deleteById(id);
    }
}




