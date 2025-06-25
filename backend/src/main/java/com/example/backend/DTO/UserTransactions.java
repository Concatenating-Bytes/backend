package com.example.backend.DTO;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Data
public class UserTransactions {
    private UUID transactionId;
    private float amount;
    private UUID sender;
    private UUID receiver;
    private Instant time;
    private boolean status;

    public UserTransactions(){

    }

    public UserTransactions(UUID id, float amount, UUID sender, UUID receiver, Instant time, boolean status) {
        this.transactionId = id;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.status = status;
    }

    public UUID getId() {
        return transactionId;
    }

    public void setId(UUID id) {
        this.transactionId = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }


    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }
}
