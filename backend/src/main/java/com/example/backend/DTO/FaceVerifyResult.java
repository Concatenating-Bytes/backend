package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class FaceVerifyResult {
    private boolean verified;
    private Float similarity;
    private String message;
    private UUID matched_user_id;
}
