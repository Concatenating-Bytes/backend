package com.example.backend.Controller;

import com.example.backend.Entity.Bank;
import com.example.backend.Service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    //create bank
    @PostMapping("/create")
    public Bank createBank(@RequestBody Bank bank){
        return bankService.createBank(bank);
    }

    //get all banks
    @GetMapping("/all")
    public List<Bank> getAllBanks(){
        return bankService.getAllBanks();
    }

    //get bank by id
    @GetMapping("/id/{id}")
    public Bank getBankById(@PathVariable UUID id){
        return bankService.getBankById(id);
    }

    //get bank by ifsc
    @GetMapping("/ifsc/{ifsc}")
    public Bank getBankByIfsc(@PathVariable String ifsc){
        return bankService.getBankByIfsc(ifsc);
    }

    //delete bank
    @DeleteMapping("/{id}")
    public  String deleteBank(@PathVariable UUID id){
        bankService.deleteBank(id);
        return  "Bank deleted successfully";
    }
}
