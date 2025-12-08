package com.example.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Banks")
@Scope("prototype")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Bank name is mandatory")
    @Column(name = "bank_name",nullable = false)
    private String name;

    @NotNull(message = "ifsc code is required")
    @Column(name = "ifsc_code",nullable = false,unique = true)
    private UUID ifscCode;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserBankDetails> details;

}
