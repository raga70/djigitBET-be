package com.djigitbet.djigitbet.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlayerDTO extends UserDTO {
    private String name;
    private String surname;
    private String nationalIDNumber;
    private String email;
    private String phoneNumber;
    private double winCoefficient;
    private double balance;
}
