package com.djigitbet.djigitbet.ServiceTest.Service;

import com.djigitbet.djigitbet.Repositories.ICassinoRepository;
import com.djigitbet.djigitbet.Repositories.IPlayerRepository;
import com.djigitbet.djigitbet.Model.DTO.AggregatedStatisticsDTO;
import com.djigitbet.djigitbet.Model.Entity.Casino;
import com.djigitbet.djigitbet.Services.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

public class StatisticsServiceTest {
    
    private IPlayerRepository playerRepositoryMock;
    private ICassinoRepository cassinoRepositoryMock;
    private StatisticsService statisticsService;
    private AggregatedStatisticsDTO aggregatedStatisticsDTO;
    
    @BeforeEach
    public void setUp(){
        playerRepositoryMock = mock(IPlayerRepository.class);
        cassinoRepositoryMock = mock(ICassinoRepository.class);
        aggregatedStatisticsDTO = mock(AggregatedStatisticsDTO.class);
        
        statisticsService = new StatisticsService(playerRepositoryMock, cassinoRepositoryMock);
    }
    
    @Test
    public void getAggregatedStatistics_pass(){
        Mockito.when(cassinoRepositoryMock.getCasinoById(1)).thenReturn(new Casino());
       
        //Arrange
        List<Double[]> list = new ArrayList<>();
        Double[] doubles = new Double[]{1.0, 2.0, 3.0};
        Double[] doubles2 = new Double[]{1.0, 2.0, 3.0};

        //Act
        list.add(doubles);
        list.add(doubles2);
        Mockito.when(playerRepositoryMock.getAggregatedStatistics()).thenReturn(list);
        
        //Assert
        assertNotNull(statisticsService.GetAggregatedStatistics());
    }
 }
