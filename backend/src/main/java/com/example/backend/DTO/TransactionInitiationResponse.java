package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class TransactionInitiationResponse {
    private UUID transaction_id;
    private boolean requires_face_verification=true;
    private String status;
    private String message;

}
