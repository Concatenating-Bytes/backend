package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false,updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBankDetails> bankDetails = new ArrayList<>();


    @NotBlank(message = "First name is required")
    @Size(max = 50)
    @Column(name ="first_name",nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Past(message = "Date of birth must be in the past")
    @Column(name="date_of_birth",nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Email(message = "email should be valid")
    @Size(max = 255)
    @Column(name = "email",unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be valid")
    @Column(name ="phone_no", nullable = false,unique = true)
    private String phoneNo;

    @Column(name="upid",unique = true)
    private String upId;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Transactions> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Transactions> receivedTransactions;

//    private int age;

    @Column(name = "face_enrolled",nullable = false)
    private boolean faceEnrolled=false;

    @Column(name = "face_enrolled_at")
    private Instant faceEnrolledAt;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", bankDetails=" + bankDetails +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phoneNo + '\'' +
                ", upId='" + upId + '\'' +
                ", sentTransactions=" + sentTransactions +
                ", receivedTransactions=" + receivedTransactions +
                '}';
    }
}
