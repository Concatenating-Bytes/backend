package com.example.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
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

    @NotNull(message = "A transaction must have a sender-id")
    @Column(name = "sender_id",updatable = false,nullable = false)
    private UUID senderId;

    @NotNull(message = "A transcatrion must have a reciever-id")
    @Column(name = "receiver_id",updatable = false,nullable = false)
    private UUID receiverId;


    @Column(name = "completed")
    private boolean status;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false,nullable = false)
    private Instant createdAt;

    public Transactions(){

    }

    public Transactions(UUID id, float amount, UUID senderId, UUID receiverId, boolean status, Instant createdAt) {
        this.id = id;
        this.amount = amount;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @NotNull(message = "Some amount should be mentioned")
    public float getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Some amount should be mentioned") float amount) {
        this.amount = amount;
    }

    public @NotNull(message = "A transaction must have a sender-id") UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(@NotNull(message = "A transaction must have a sender-id") UUID senderId) {
        this.senderId = senderId;
    }

    public @NotNull(message = "A transcatrion must have a reciever-id") UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(@NotNull(message = "A transcatrion must have a reciever-id") UUID receiverId) {
        this.receiverId = receiverId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", amount=" + amount +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
