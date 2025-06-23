package com.example.backend.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import org.hibernate.annotations.NotFound;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Entity
@Table(name = "users")
@Scope("prototype")
public class User {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid",updatable = false,nullable = false)
    private UUID id;

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

    public User(){

    }

    public User(UUID id, String firstName, String lastName, String dateOfBirth, String email, String phone_no) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone_no = phone_no;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @NotBlank(message = "first_name is required") @Size(max = 50) String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "first_name is required") @Size(max = 50) String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "last_name is required") @Size(max = 50) String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "last_name is required") @Size(max = 50) String lastName) {
        this.lastName = lastName;
    }

    public @Min(1900) String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@Min(1900) String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public @Email(message = "email should be valid") @Max(1000) String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "email should be valid") @Max(1000) String email) {
        this.email = email;
    }

    public @NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be valid") String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(@NotBlank(message = "Phone number is required") @Pattern(regexp = "\\d{10}", message = "Phone number must be valid") String phone_no) {
        this.phone_no = phone_no;
    }

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
