package com.example.backend.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBankInfo {
    private long id;
    private UUID user_id;
    private String account_holder_name;
    private String account_number;
    private UUID type_id;
    private Instant created_at;
    private UUID ifsc_code;
    private float balance;
}
