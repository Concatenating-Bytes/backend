package com.example.backend.Mapper;

import com.example.backend.DTO.UserBankInfo;
import com.example.backend.Entity.AccountType;
import com.example.backend.Entity.Bank;
import com.example.backend.Entity.User;
import com.example.backend.Entity.UserBankDetails;

public class UserBankMapper {

    // DTO → ENTITY
    public static UserBankDetails toEntity(UserBankInfo dto, User user, Bank bank, AccountType accountType) {

        UserBankDetails bd = new UserBankDetails();

        bd.setAccountHolderName(dto.getAccountHolderName());
        bd.setAccountNumber(dto.getAccountNumber());
        bd.setBalance(dto.getBalance());

        bd.setUser(user);        //  mandatory
        bd.setBank(bank);     //  Bank entity
        bd.setType(accountType);
        return bd;
    }

    // ENTITY → DTO
    public static UserBankInfo toDto(UserBankDetails bd) {

        return new UserBankInfo(
                bd.getId(),
                bd.getUser().getId(),
                bd.getAccountHolderName(),
                bd.getAccountNumber(),
                bd.getCreatedAt(),
                bd.getBank().getIfscCode(),
                bd.getBalance(),
                bd.getType() != null ? bd.getType().getId() : null
        );
    }
}
