package com.example.backend.DTO;

import lombok.Data;

@Data
public class TransactionVerifyFaceResponse {
    private String status;
    private Float similarity;
    private String message;
}
