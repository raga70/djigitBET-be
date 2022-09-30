package com.djigitbet.djigitbet.Model.Entity;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.PlayerDTO;
import com.djigitbet.djigitbet.Model.DTO.UserDTO;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
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
