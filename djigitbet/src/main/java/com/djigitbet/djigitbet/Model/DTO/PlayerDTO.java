package com.djigitbet.djigitbet.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDTO extends UserDTO  {
    private String name;
    private String surname;
    private String nationalIDNumber;
    private String email;
    private String phoneNumber;
    private double WinCoefficient;
    private double balance;
}
