package com.example.backend.Controller;

import com.example.backend.Entity.AccountType;
import com.example.backend.Service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-type")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService service;

    //  CREATE
    @PostMapping("/create")
    public AccountType create(@RequestBody AccountType type) {
        return service.create(type);
    }

    //  GET ALL
    @GetMapping("/all")
    public List<AccountType> getAll() {
        return service.getAllAccountType();
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public AccountType getById(@PathVariable Long id) {
        return service.getById(id);
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Account Type deleted successfully";
    }
}
