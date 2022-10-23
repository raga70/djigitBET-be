package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.AggregatedStatisticsDTO;
import com.djigitbet.djigitbet.Services.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {StatisticsController.class})
@ExtendWith(SpringExtension.class)
class StatisticsControllerTest {
    @Autowired
    private StatisticsController statisticsController;

    @MockBean
    private StatisticsService statisticsService;

    /**
     * Method under test: {@link StatisticsController#getAggregatedStatistics()}
     */
    @Test
    void testGetAggregatedStatistics() throws Exception {
        when(statisticsService.GetAggregatedStatistics())
                .thenReturn(new AggregatedStatisticsDTO(10.0d, 10.0d, 10.0d, 10.0d, 10.0d));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statistics/");
        MockMvcBuilders.standaloneSetup(statisticsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"totalFundsPayedIn\":10.0,\"totalFundsPayedOut\":10.0,\"jackpot\":10.0,\"casinoFunds\":10.0,\"totalWinCoefficient"
                                        + "\":10.0}"));
    }
}

