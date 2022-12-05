package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.placeBetDTO;
import com.djigitbet.djigitbet.Services.SlotEngine;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SlotsController.class})
@ExtendWith(SpringExtension.class)
class SlotsControllerTest {
    @MockBean
    private JWTUtil jWTUtil;

    @MockBean
    private SlotEngine slotEngine;

    @Autowired
    private SlotsController slotsController;

    @MockBean
    private UserService userService;

    private placeBetDTO createPlaceBetDTO(){
        placeBetDTO placeBetDTO = new placeBetDTO();
        placeBetDTO.setBetAmount(10.0d);
        return placeBetDTO;
    }

    /**
     * Method under test: {@link SlotsController#getJackpot()}
     */
    @Test
    void testGetJackpot() throws Exception {
        //Act
        when(slotEngine.getJackpot()).thenReturn(10.0d);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/slots/jackpot");

        //Assert
        MockMvcBuilders.standaloneSetup(slotsController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }

    /**
     * Method under test: {@link SlotsController#PlaceBet(String, placeBetDTO)}
     */
    @Test
    void testPlaceBet() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createPlaceBetDTO());

        //Act
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/slots/")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(slotsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }

    /**
     * Method under test: {@link SlotsController#PlaceBet(String, placeBetDTO)}
     */
    @Test
    void testPlaceBet2() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createPlaceBetDTO());

        //Act
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/slots/", "Uri Variables")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(slotsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }

    /**
     * Method under test: {@link SlotsController#PlaceBet(String, placeBetDTO)}
     */
    @Test
    void testPlaceBet3() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createPlaceBetDTO());
       
        //Act
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/slots/")
                .header("Authorization", "Values", "Values")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(slotsController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized"));
    }
}

