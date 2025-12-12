package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.backend.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Scope("prototype")
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue
    @Column(name = "id",nullable = false,updatable = false)
    private UUID id;

    @NotNull(message = "Some amount should be mentioned")
    @Column(name = "amount",nullable = false,updatable = false)
    private float amount;

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    @JsonIgnore
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver_id",nullable = false)
    @JsonIgnore
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private TransactionStatus status=TransactionStatus.PENDING;

    @Column(name = "approved_at")
    private Instant approvedAt;

    @Column(name = "failure_reason")
    private String failureReason;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false,nullable = false)
    private Instant createdAt;


    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", amount=" + amount +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
