package com.djigitbet.djigitbet.Model.Entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Casino {
    @Id
    int id = 1;
    double casinoFunds;
    double jackpot;
    double totalWinCoefficient;


}
