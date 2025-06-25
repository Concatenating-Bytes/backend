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

    @ManyToOne
    @JoinColumn(name = "sender_id",nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name="receiver_id",nullable = false)
    private User receiver;


    @Column(name = "completed")
    private boolean status;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false,nullable = false)
    private Instant createdAt;



    public Transactions(){

    }

    public Transactions(UUID id, float amount, User sender, User receiver, boolean status, Instant createdAt) {
        this.id = id;
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
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

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

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
