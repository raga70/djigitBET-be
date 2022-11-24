package com.djigitbet.djigitbet.Model.Entity;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Casino {
    @Id
    int id = 1;
    double casinoFunds;
    double jackpot;
    double totalWinCoefficient;
}
