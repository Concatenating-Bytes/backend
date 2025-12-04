package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class FaceVerifyRequest {
    private UUID user_id;
    private String image;
}
