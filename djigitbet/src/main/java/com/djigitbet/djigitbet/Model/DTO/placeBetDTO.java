package com.djigitbet.djigitbet.Model.DTO;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class placeBetDTO {
    @Min(1)
    private double betAmount;
}
