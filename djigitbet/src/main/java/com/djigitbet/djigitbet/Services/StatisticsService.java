package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Repositories.ICassinoRepository;
import com.djigitbet.djigitbet.Repositories.IPlayerRepository;
import com.djigitbet.djigitbet.Model.DTO.AggregatedStatisticsDTO;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final IPlayerRepository playerRepository;

    private final ICassinoRepository casinoRepository;

    public StatisticsService(IPlayerRepository playerRepository1, ICassinoRepository casinoRepository1){

        this.playerRepository = playerRepository1;
        this.casinoRepository = casinoRepository1;
    }

    public AggregatedStatisticsDTO GetAggregatedStatistics() {
        var aggregatedStatistics = new AggregatedStatisticsDTO();
        var totalPlayerFunds = playerRepository.getAggregatedStatistics();
        double totalFundsPayedIn = totalPlayerFunds.get(0)[0]; //jpa cannot map to totalPlayerFundsDTO, and only gives you an object array without names when you need to do a custom query , because jpa is missing basic features that other orms provide like SUM(), AVG()
        double totalFundsPayedOut = totalPlayerFunds.get(0)[1];
        var casino = casinoRepository.getCasinoById(1);
        aggregatedStatistics.setTotalFundsPayedIn(totalFundsPayedIn);
        aggregatedStatistics.setTotalFundsPayedOut(totalFundsPayedOut);
        aggregatedStatistics.setJackpot(casino.getJackpot());
        aggregatedStatistics.setCasinoFunds(casino.getCasinoFunds());
        aggregatedStatistics.setTotalWinCoefficient(casino.getTotalWinCoefficient());
        return aggregatedStatistics;
    }


}
