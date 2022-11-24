package com.djigitbet.djigitbet.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AggregatedStatisticsDTO {
    double totalFundsPayedIn;
    double totalFundsPayedOut;
    double jackpot;
    double casinoFunds;
    double totalWinCoefficient;


}
