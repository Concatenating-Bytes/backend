package com.example.backend.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class FaceEnrollRequest {
    private UUID user_id;
    @com.fasterxml.jackson.annotation.JsonProperty("image_b64")
    private String image;
}
