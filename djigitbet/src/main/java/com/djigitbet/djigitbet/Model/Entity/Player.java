package com.djigitbet.djigitbet.Model.Entity;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Player extends User {
     
    private String name;
     
    private String surname;

     
    private String nationalIDNumber;
     
    
    private String email;
     
    private String phoneNumber;


    private double winCoefficient;
    private double balance;


    private double fundsLost;
    private double fundsPayedOut;


    public Player(EditPlayerRequestDTO editPlayerRequestDTO) {
        super(editPlayerRequestDTO);
        this.name = editPlayerRequestDTO.getName();
        this.surname = editPlayerRequestDTO.getSurname();
        this.nationalIDNumber = editPlayerRequestDTO.getNationalIDNumber();
        this.email = editPlayerRequestDTO.getEmail();
        this.phoneNumber = editPlayerRequestDTO.getPhoneNumber();
    }


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
        return winCoefficient;
    }

    public void setWinCoefficient(double winCoefficient) {
        this.winCoefficient = winCoefficient;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFundsPayedOut() {
        return fundsPayedOut;
    }

    public void setFundsPayedOut(double fundsPayedOut) {
        this.fundsPayedOut = fundsPayedOut;
    }

    public double getFundsLost() {
        return fundsLost;
    }

    public void setFundsLost(double fundsLost) {
        this.fundsLost = fundsLost;
    }
}
