package com.example.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "face_verification_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaceVerificationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean verified;

    private Float similarityScore;

    @CreationTimestamp
    private Instant timestamp;

    private String ipAddress;
}
