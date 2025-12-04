package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionInitiationRequest {
    private UUID user_id;
    private Float amount;
    private String receiver;
    private String details;
}
