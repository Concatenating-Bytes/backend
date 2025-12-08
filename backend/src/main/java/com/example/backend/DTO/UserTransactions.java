package com.example.backend.DTO;
import com.example.backend.enums.TransactionStatus;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserTransactions {
    private UUID transactionId;
    private float amount;
    private UUID sender;
    private UUID receiver;
    private Instant time;
    private TransactionStatus status;
}
