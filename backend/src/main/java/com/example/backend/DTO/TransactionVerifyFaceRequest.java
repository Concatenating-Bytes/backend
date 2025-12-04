package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionVerifyFaceRequest {
    private UUID transaction_id;
    private UUID user_id;
    private String image;
}
