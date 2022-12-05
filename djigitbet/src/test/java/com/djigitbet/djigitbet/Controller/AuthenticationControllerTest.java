package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.Entity.LoginCredentials;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.management.relation.Role;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private AuthenticationManagerUserService authenticationManagerUserService;

    @MockBean
    private JWTUtil jWTUtil;

    @MockBean
    private UserService userService;
    
    private User user;
    
    @BeforeEach
    public void setUp() {
        
    }
    
    private User createUser(){
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        return user;
    }
    
    private LoginCredentials createLoginCredentials(){
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setPassword("iloveyou");
        loginCredentials.setUsername("janedoe");
        return loginCredentials;
    }
    
    private Player createPlayer(){
        Player player = new Player();
        player.setBalance(10.0d);
        player.setEmail("jane.doe@example.org");
        player.setFundsLost(10.0d);
        player.setFundsPayedOut(10.0d);
        player.setName("?");
        player.setNationalIDNumber("42");
        player.setPassword("iloveyou");
        player.setPhoneNumber("4105551212");
        player.setSurname("Doe");
        player.setType(UserType.PLAYER);
        player.setUserID(1);
        player.setUsername("janedoe");
        player.setWinCoefficient(10.0d);
        player.setPassword("iloveyou");
        player.setType(UserType.PLAYER);
        player.setUserID(1);
        player.setUsername("janedoe");
        return player;
    }
    
    private EditPlayerRequestDTO createEditPlayerRequestDTO(){
        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("jane.doe@example.org");
        editPlayerRequestDTO.setName("Name");
        editPlayerRequestDTO.setNationalIDNumber("42");
        editPlayerRequestDTO.setPassword("iloveyou");
        editPlayerRequestDTO.setPhoneNumber("4105551212");
        editPlayerRequestDTO.setSurname("Doe");
        editPlayerRequestDTO.setType(UserType.PLAYER);
        editPlayerRequestDTO.setUserID(1);
        editPlayerRequestDTO.setUsername("janedoe");
        return editPlayerRequestDTO;
    }
        
    /**
     * Method under test: {@link AuthenticationController#getToken(LoginCredentials)}
     */
    @Test
    void testGetToken() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createLoginCredentials());
        
        //Act
        when(authenticationManagerUserService.Login((String) any(), (String) any())).thenReturn(true);
        when(jWTUtil.createJWT((User) any())).thenReturn("Create JWT");
        when(userService.GetUser((String) any())).thenReturn(createUser());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":null,\"surname\":null,\"nationalIDNumber\":null,"
                                        + "\"email\":null,\"phoneNumber\":null,\"winCoefficient\":0.0,\"balance\":0.0}"));
    }

    /**
     * Method under test: {@link AuthenticationController#getToken(LoginCredentials)}
     */
    @Test
    void testGetToken2() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createLoginCredentials());
        
        //Act
        when(authenticationManagerUserService.Login(any(),any())).thenReturn(false);
        when(jWTUtil.createJWT(any())).thenReturn("Create JWT");
        when(userService.GetUser(any())).thenReturn(createUser());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AuthenticationController#getToken(LoginCredentials)}
     */
    @Test
    void testGetToken3() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createLoginCredentials());
        
        //Act
        when(authenticationManagerUserService.Login(any(),any())).thenReturn(true);
        when(jWTUtil.createJWT(any())).thenReturn("?");
        when(userService.GetUser(any())).thenReturn(createUser());
        
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":null,\"surname\":null,\"nationalIDNumber\":null,"
                                        + "\"email\":null,\"phoneNumber\":null,\"winCoefficient\":0.0,\"balance\":0.0}"));
    }

    /**
     * Method under test: {@link AuthenticationController#getToken(LoginCredentials)}
     */
    @Test
    void testGetToken4() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createLoginCredentials());
        
        //Act
        when(authenticationManagerUserService.Login(any(),any())).thenReturn(true);
        when(jWTUtil.createJWT(any())).thenReturn("Create JWT");
        when(userService.GetUser((String) any())).thenReturn(createPlayer());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
       
        //Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\",\"surname\":\"Doe\",\"nationalIDNumber\":\"42\","
                                        + "\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"4105551212\",\"winCoefficient\":10.0,\"balance\":10.0}"));
    }

    /**
     * Method under test: {@link AuthenticationController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());
        
        //Act
        when(userService.SaveUser(any())).thenReturn(createUser());
     
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("the player has been added"));
    }

    /**
     * Method under test: {@link AuthenticationController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser2() throws Exception {
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);

        //Act
        editPlayerRequestDTO.setEmail("?");
        editPlayerRequestDTO.setName("Name");
        
        when(userService.SaveUser(any())).thenReturn(createUser());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Method under test: {@link AuthenticationController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser3() throws Exception { 
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO = createEditPlayerRequestDTO();
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        
        //Act
        when(userService.SaveUser(any())).thenReturn(createUser());
        editPlayerRequestDTO.setType(UserType.ADMIN);
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"));
    }
}

