package com.djigitbet.djigitbet.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AggregatedStatisticsDTO {
    double totalFundsPayedIn;
    double totalFundsPayedOut;
    double jackpot;
    double casinoFunds;
    double totalWinCoefficient;


}
