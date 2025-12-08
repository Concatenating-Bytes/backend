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
    private UUID userId;
    private String accountHolderName;
    private String accountNumber;
    private Instant createdAt;
    private UUID ifscCode;
    private float balance;
    private Long typeId;

}
