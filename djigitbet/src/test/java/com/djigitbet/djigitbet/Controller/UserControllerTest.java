package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;
    
    private ArrayList<User> users = new ArrayList<User>();
    
    private ArrayList<Player> players = new ArrayList<Player>();
    
    private void addUsers() {
    	User user = createUser();
        User user2 = createUser();
    	users.add(user);
        users.add(user2);
    }
    
    private void addUser(){
        User user = createUser();
        users.add(user);
    }
    
    private void addPlayers(){
        Player player = createPlayer();
        Player player2 = createPlayer();
        players.add(player);
        players.add(player2);
    }
    
    private void addPlayer(){
        Player player = createPlayer();
        players.add(player);
    }

    private User createUser(){
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        return user;
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

    /**
     * Method under test: {@link UserController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());
        
        //Act
        when(userService.SaveUser(any())).thenReturn(createUser());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("the player has been added"));
    }

    /**
     * Method under test: {@link UserController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser2() throws Exception {
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO = createEditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("?");

        //Act
        when(userService.SaveUser(any())).thenReturn(createUser());
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Validation failed"));
    }

    /**
     * Method under test: {@link UserController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser3() throws Exception {
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO = createEditPlayerRequestDTO();
        editPlayerRequestDTO.setType(UserType.ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);

        //Act
        when(userService.SaveUser(any())).thenReturn(createUser());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("the user has been added"));
    }

    /**
     * Method under test: {@link UserController#GetAllUsers(HttpServletResponse)}
     */
    @Test
    void testGetAllUsers() throws Exception {
        //Act
        when(userService.GetAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");

        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#GetAllUsers(HttpServletResponse)}
     */
    @Test
    void testGetAllUsers2() throws Exception {
        //Act
        addUser();
        when(userService.GetAllUsers()).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":null,\"surname\":null,\"nationalIDNumber\":null"
                                        + ",\"email\":null,\"phoneNumber\":null,\"winCoefficient\":0.0,\"balance\":0.0}]"));
    }

    /**
     * Method under test: {@link UserController#GetAllUsers(HttpServletResponse)}
     */
    @Test
    void testGetAllUsers3() throws Exception {
        //Act
        addUsers();
        when(userService.GetAllUsers()).thenReturn(users);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":null,\"surname\":null,\"nationalIDNumber\":null"
                                        + ",\"email\":null,\"phoneNumber\":null,\"winCoefficient\":0.0,\"balance\":0.0},{\"userID\":1,\"type\":\"PLAYER\","
                                        + "\"username\":\"janedoe\",\"name\":null,\"surname\":null,\"nationalIDNumber\":null,\"email\":null,\"phoneNumber\""
                                        + ":null,\"winCoefficient\":0.0,\"balance\":0.0}]"));
    }

    /**
     * Method under test: {@link UserController#GetUser(int)}
     */
    @Test
    void testGetUser() throws Exception {
        //Act
        when(userService.GetUser(anyInt())).thenReturn(createUser());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
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
     * Method under test: {@link UserController#GetUser(int)}
     */
    @Test
    void testGetUser2() throws Exception {
        //Act
        when(userService.GetUser(anyInt())).thenReturn(createPlayer());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
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
     * Method under test: {@link UserController#GetUser(int)}
     */
    @Test
    void testGetUser3() throws Exception {
        //Arrange
        Player player = createPlayer();
        
        //Act
        player.setEmail("?");
        when(userService.GetUser(anyInt())).thenReturn(player);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\",\"surname\":\"Doe\",\"nationalIDNumber\":\"42\","
                                        + "\"email\":\"?\",\"phoneNumber\":\"4105551212\",\"winCoefficient\":10.0,\"balance\":10.0}"));
    }

    /**
     * Method under test: {@link UserController#UpdateUser(EditPlayerRequestDTO, int)}
     */
    @Test
    void testUpdateUser() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());

        //Act
        when(userService.UpdateUser(any(), anyInt())).thenReturn(createUser());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        //Assert
        MockMvcBuilders.standaloneSetup(userController)
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
     * Method under test: {@link UserController#UpdateUser(EditPlayerRequestDTO, int)}
     */
    @Test
    void testUpdateUser2() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());
        
        //Act
        when(userService.UpdateUser(any(), anyInt())).thenReturn(createPlayer());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
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
     * Method under test: {@link UserController#UpdateUser(EditPlayerRequestDTO, int)}
     */
    @Test
    void testUpdateUser3() throws Exception {
        //Arrange
        EditPlayerRequestDTO editPlayerRequestDTO = createEditPlayerRequestDTO();
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);

        //Act
        when(userService.UpdateUser(any(), anyInt())).thenReturn(createUser());
        editPlayerRequestDTO.setEmail("?");
  
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#UpdateUser(EditPlayerRequestDTO, int)}
     */
    @Test
    void testUpdateUser4() throws Exception {
        //Arrange
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());

        //Act
        when(userService.UpdateUser(any(), anyInt())).thenReturn(createUser());
        
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
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
     * Method under test: {@link UserController#UpdateUser(EditPlayerRequestDTO, int)}
     */
    @Test
    void testUpdateUser5() throws Exception {
        //Arrange        
        Player player = createPlayer();
        String content = (new ObjectMapper()).writeValueAsString(createEditPlayerRequestDTO());
        
        //Act
        player.setEmail("?");
        player.setName("?");
        when(userService.UpdateUser(any(), anyInt())).thenReturn(player);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        
        //Assert        
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\",\"surname\":\"Doe\",\"nationalIDNumber\":\"42\","
                                        + "\"email\":\"?\",\"phoneNumber\":\"4105551212\",\"winCoefficient\":10.0,\"balance\":10.0}"));
    }

    /**
     * Method under test: {@link UserController#DeleteUser(int)}
     */
    @Test
    void testDeleteUser() throws Exception {
        //Act
        doNothing().when(userService).DeleteUser(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/{id}", 1);
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#DeleteUser(int)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        //Act        
        doNothing().when(userService).DeleteUser(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/user/{id}", 1);
        deleteResult.characterEncoding("Encoding");
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#GetAllPlayersPagged(int, int, String)}
     */
    @Test
    void testGetAllPlayersPagged() throws Exception {
        //Act
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#GetAllPlayersPagged(int, int, String)}
     */
    @Test
    void testGetAllPlayersPagged2() throws Exception {
        //Act
        addPlayer();
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), (String) any())).thenReturn(players);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
       
       
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\",\"surname\":\"Doe\",\"nationalIDNumber\":\"42\""
                                        + ",\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"4105551212\",\"winCoefficient\":10.0,\"balance\":10.0,"
                                        + "\"fundsLost\":10.0,\"fundsPayedOut\":10.0}]"));
    }

    /**
     * Method under test: {@link UserController#GetAllPlayersPagged(int, int, String)}
     */
    @Test
    void testGetAllPlayersPagged3() throws Exception {
        //Act
        addPlayers();
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), (String) any())).thenReturn(players);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
        
        //Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\",\"surname\":\"Doe\",\"nationalIDNumber\":\"42\""
                                        + ",\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"4105551212\",\"winCoefficient\":10.0,\"balance\":10.0,"
                                        + "\"fundsLost\":10.0,\"fundsPayedOut\":10.0},{\"userID\":1,\"type\":\"PLAYER\",\"username\":\"janedoe\",\"name\":\"?\","
                                        + "\"surname\":\"Doe\",\"nationalIDNumber\":\"42\",\"email\":\"jane.doe@example.org\",\"phoneNumber\":\"4105551212\","
                                        + "\"winCoefficient\":10.0,\"balance\":10.0,\"fundsLost\":10.0,\"fundsPayedOut\":10.0}]"));
    }
}

