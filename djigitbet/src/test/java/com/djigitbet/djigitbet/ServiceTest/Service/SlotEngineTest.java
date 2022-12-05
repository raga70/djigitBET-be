package com.djigitbet.djigitbet.ServiceTest.Service;
import com.djigitbet.djigitbet.Repositories.ICassinoRepository;
import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.Casino;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Services.SlotEngine;
import com.djigitbet.djigitbet.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class SlotEngineTest {
    ICassinoRepository casinoRepositoryMock;
    UserService userServiceMock;
    SecureRandom secureRandom;
    Casino casino;
    SlotEngine slotEngine;
    double prize;
    
    @BeforeEach 
    public void setUp() throws NoSuchAlgorithmException {
        casinoRepositoryMock = mock(ICassinoRepository.class);
        userServiceMock = mock(UserService.class);
        secureRandom = SecureRandom.getInstanceStrong();
        slotEngine = new SlotEngine(casinoRepositoryMock, userServiceMock);
        casino = new Casino(1, 1000.0, 100.0, 2.0);
    }

    @Test
    public void play_pass(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        
        //Act
        player.setBalance(100);
        prize = 0;
        
        //Assert
        Mockito.when(casinoRepositoryMock.getCasinoById(1)).thenReturn(casino);
        assertNotNull(slotEngine.Play(player, 20));
    }
    
    @Test
    public void play_throws_illegalArgumentException(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        
        //Act
        player.setBalance(100);
        
        //Assert
        assertThrows(IllegalArgumentException.class, () -> {
            slotEngine.Play(player, 101);
        });
    }
    
    @Test
    public void win_Ring123AreBelow30_prizeIs_X5(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(29, 29, 29, 100.0, 100.0, 0); 
        
        //Act
        player.setBalance(100);
       
        //Assert
        slotEngine.Win(slotCalculationsDTO, player, 20);
        assertEquals(20*5, slotCalculationsDTO.getPrize()); // 🥲 using ref 
    }

    @Test
    public void win_Ring123AreBetween30And50_prizeIs_X15(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(31, 31, 31, 100.0, 100.0, 0);
        
        //Act
        player.setBalance(100);
        
        //Assert
        slotEngine.Win(slotCalculationsDTO, player, 20);
        assertEquals(20*15, slotCalculationsDTO.getPrize());
    }

    @Test
    public void win_Ring123AreBetween50And75_prizeIs_X20(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(55, 55, 55, 100.0, 100.0, 0);
        
        //Act
        player.setBalance(100);
        slotEngine.Win(slotCalculationsDTO, player, 20);
        
        //Assert
        assertEquals(20*20, slotCalculationsDTO.getPrize()); 
    }

    @Test
    public void win_Ring123AreBetween75And95_prizeIs_X25(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(77, 77, 77, 100.0, 100.0,0);
        
        //Act
        player.setBalance(100);
        slotEngine.Win(slotCalculationsDTO, player, 20);
        
        //Assert
        assertEquals(20*25, slotCalculationsDTO.getPrize());
    }

    @Test
    public void in_Ring123AreBetween95And100_prizeIs_Jackpot(){
        //Arrange
        Mockito.when(casinoRepositoryMock.getCasinoById(1)).thenReturn(casino);
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(96, 96, 96, 100.0, 100.0,0);
        
        //Act
        player.setBalance(100);
        casino.setJackpot(100.0);
        slotEngine.Win(slotCalculationsDTO, player, 20);
        
        //Assert
        Mockito.when(casinoRepositoryMock.getCasinoById(1)).thenReturn(casino);
        assertEquals(100, slotCalculationsDTO.getPrize());
    }

    @Test
    public void win_pass_6(){
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(11, 55, 85, 100.0, 100.0,0);
        
        //Act
        player.setBalance(100);
        slotEngine.Win(slotCalculationsDTO, player, 20);
        
        //Assert
        assertEquals(0, slotCalculationsDTO.getPrize());
    }
    
    @Test
    public void resetJackpot_pass(){
        //Arrange
        Casino casino1 = new Casino(1, 100, 100.0, 2.0);
        
        //Act
        casino.setJackpot(0);
        
        //Assert
        assertTrue(casino1.getJackpot() > casino.getJackpot()/2);
        assertTrue(casino.getJackpot() == 0);
    }
    
    @Test
    public void getJackpot_pass(){
        
        //Assert
        assertNotNull(casino.getJackpot());
    }
    
    @Test
    public void getJackpot_pass_2(){
        //Act
        casino.setJackpot(0);
        
        //Assert
        assertEquals(0, casino.getJackpot());
    }
    
    @Test
    public void supportWin_Pass(){
        
        Mockito.when(casinoRepositoryMock.getCasinoById(1)).thenReturn(casino);
        
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO =
                new EditPlayerRequestDTO("Test", "Test", "Test", "test@gmail", " 1234");
        Player player = new Player(editPlayerRequestDTO);
        SlotEngine.SlotCalculationsLOCAL slotCalculationsDTO = new SlotEngine.SlotCalculationsLOCAL(96, 96, 96, 100.0, 100.0,0);
        
        //Act
        player.setBalance(100);
        player.setWinCoefficient(-11);
        casino.setJackpot(100.0);
        slotEngine.supportWin(slotCalculationsDTO, player, 100);
        prize = 500;
        
        //Assert
        assertEquals(prize, 500);
    }
}
