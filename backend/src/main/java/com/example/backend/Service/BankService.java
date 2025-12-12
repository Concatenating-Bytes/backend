package com.example.backend.Service;

import com.example.backend.Entity.Bank;
import com.example.backend.Repo.BankRepo;
import io.swagger.v3.oas.models.links.Link;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepo bankRepo;

    //create bank
    public Bank createBank(Bank bank){
        return bankRepo.save(bank);
    }

    //get all banks
    public List<Bank> getAllBanks(){
        return bankRepo.findAll();
    }

    //get bank by id
    public Bank getBankById(UUID id){
        return bankRepo.findById(id).orElseThrow(()->new RuntimeException("Bank not found"));
    }

    //get bank by ifsc
    public Bank getBankByIfsc(String ifsc){
        return bankRepo.findByIfscCode(ifsc).orElseThrow(()->new RuntimeException("Bank not found with the IFSC:"+ifsc));
    }

    //delete bank
    public  void deleteBank(UUID id){
        bankRepo.deleteById(id);
    }
}
