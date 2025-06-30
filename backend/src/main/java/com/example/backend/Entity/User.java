package com.example.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Scope("prototype")
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid",updatable = false,nullable = false)
    private UUID id;

    @ManyToMany
    @JoinTable(name = "account_user_id")
    private List<UserBankDetails> bankDetails;

    @NotBlank(message = "first_name is required")
    @Size(max = 50)
    @Column(name ="first_name",updatable = false ,nullable = false)
    private String firstName;

    @NotBlank(message = "last_name is required")
    @Size(max = 50)
    @Column(name = "last_name",updatable = false,nullable = false)
    private String lastName;

    @Min(1900)
    @Column(name="date_of_birth",updatable = false,nullable = false)
    private String dateOfBirth;

    @Email(message = "email should be valid")
    @Max(1000)
    @Column(name = "email",unique = true)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be valid")
    @Column(nullable = false)
    private String phone_no;

    @OneToMany(mappedBy = "sender")
    private List<Transactions> sentTransactions;

    @OneToMany(mappedBy = "receiver")
    private List<Transactions> receivedTransactions;

//    private int age;



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", phone_no='" + phone_no + '\'' +
                '}';
    }
}
