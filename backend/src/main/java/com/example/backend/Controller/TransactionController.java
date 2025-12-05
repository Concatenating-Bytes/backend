package com.example.backend.Controller;

import com.example.backend.DTO.TransactionInitiationRequest;
import com.example.backend.DTO.TransactionInitiationResponse;
import com.example.backend.DTO.TransactionVerifyFaceRequest;
import com.example.backend.DTO.TransactionVerifyFaceResponse;
import com.example.backend.Service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @PostMapping("/initiate")
    public TransactionInitiationResponse initiate(@RequestBody TransactionInitiationRequest req){
        return  service.initiate(req);
    }

    @PostMapping("/verify-face")
    public TransactionVerifyFaceResponse verify(@RequestBody TransactionVerifyFaceRequest req, HttpServletRequest request){
        return  service.verifyTransaction(req,request.getRemoteAddr());
    }
}
