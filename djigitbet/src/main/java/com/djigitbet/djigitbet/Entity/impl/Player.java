package com.djigitbet.djigitbet.Entity.impl;

import jakarta.persistence.Entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
public class Player extends User {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    
    @NotBlank
    private String nationalIDNumber;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
 
    
    private double WinCoefficient;
    private double balance;

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNationalIDNumber() {
        return nationalIDNumber;
    }

    public void setNationalIDNumber(String nationalIDNumber) {
        this.nationalIDNumber = nationalIDNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getWinCoefficient() {
        return WinCoefficient;
    }

    public void setWinCoefficient(double winCoefficient) {
        WinCoefficient = winCoefficient;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
