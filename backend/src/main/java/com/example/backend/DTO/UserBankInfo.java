package com.example.backend.DTO;
import lombok.*;


import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserBankInfo {
    private long id;
    private UUID user_id;
    private String account_holder_name;
    private String account_number;
    private Instant created_at;
    private UUID ifsc_code;
    private String account_type;
    private float balance;
}
