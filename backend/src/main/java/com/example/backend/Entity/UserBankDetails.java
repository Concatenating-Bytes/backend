package com.example.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false,unique = true)
    private long id;

    //Relationships start
    @ManyToMany(mappedBy = "bankDetails")
    private List<User> user;

    @ManyToOne
    @JoinColumn(name = "bank_ifsc",referencedColumnName = "ifsc_code",nullable = false)
    private Bank bank_ifsc;

    @ManyToOne
    @JoinColumn(name = "account_type")
    private AccountType type;
    //Relationship end

    @NotBlank(message = "Account holder name is required")
    @Size(max = 100)
    @Column(name = "holder_name")
    private String account_holder_name;

    @NotBlank(message = "Account number is requires")
    @Size(min=10)
    @Column(name = "account_number",nullable = false,unique = true)
    private String account_number;


    @CreationTimestamp
    @Column(name = "created_at",nullable = false,unique = true)
    private Instant created_at;


    @Column(name = "bank_balance")
    private float balance;

    @Override
    public String toString() {
        return "UserBankDetails{" +
                "id=" + id +
                ", user=" + user +
                ", bank_ifsc=" + bank_ifsc +
                ", account_holder_name='" + account_holder_name + '\'' +
                ", account_number='" + account_number + '\'' +
                ", type=" + type +
                ", created_at=" + created_at +
                ", balance=" + balance +
                '}';
    }
}
