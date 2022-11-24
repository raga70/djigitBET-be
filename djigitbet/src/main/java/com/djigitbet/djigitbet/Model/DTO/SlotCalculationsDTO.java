package com.djigitbet.djigitbet.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotCalculationsDTO {
    int ring1;
    int ring2;
    int ring3;
    double jackpot;
    double balance;
}
