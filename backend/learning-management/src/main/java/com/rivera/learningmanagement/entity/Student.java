package com.rivera.learningmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "The first name is mandatory.")
    @Size(min = 2, max = 50)
    private String firstName;

    @NotBlank(message = "The last name is mandatory.")
    @Size(min = 2, max = 50)
    private String lastName;

    @NotBlank(message = "The email is mandatory.")
    @Email
    @Column(unique = true)
    private String email;

    @NotNull(message = "The date of birth is mandatory.")
    @Past
    private LocalDate dateOfBirth;

    @NotBlank(message = "The address is mandatory.")
    @Size(max = 255)
    private String address;

    @NotBlank(message = "The phone number is mandatory.")
    @Size(max = 50)
    private String phoneNumber;

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Student(String firstName,
                   String lastName,
                   String email,
                   LocalDate dateOfBirth,
                   String address,
                   String phoneNumber
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
