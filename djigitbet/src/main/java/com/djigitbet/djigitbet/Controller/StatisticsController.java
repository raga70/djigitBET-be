package com.djigitbet.djigitbet.Controller;

import com.djigitbet.djigitbet.Model.DTO.AggregatedStatisticsDTO;
import com.djigitbet.djigitbet.Services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/")
    public AggregatedStatisticsDTO getAggregatedStatistics() {
        return statisticsService.GetAggregatedStatistics();
    }


}
