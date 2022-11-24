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

    /**
     * Method under test: {@link UserController#SaveUser(EditPlayerRequestDTO)}
     */
    @Test
    void testSaveUser() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.SaveUser((User) any())).thenReturn(user);

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
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.SaveUser((User) any())).thenReturn(user);

        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("?");
        editPlayerRequestDTO.setName("Name");
        editPlayerRequestDTO.setNationalIDNumber("42");
        editPlayerRequestDTO.setPassword("iloveyou");
        editPlayerRequestDTO.setPhoneNumber("4105551212");
        editPlayerRequestDTO.setSurname("Doe");
        editPlayerRequestDTO.setType(UserType.PLAYER);
        editPlayerRequestDTO.setUserID(1);
        editPlayerRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.SaveUser((User) any())).thenReturn(user);

        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("jane.doe@example.org");
        editPlayerRequestDTO.setName("Name");
        editPlayerRequestDTO.setNationalIDNumber("42");
        editPlayerRequestDTO.setPassword("iloveyou");
        editPlayerRequestDTO.setPhoneNumber("4105551212");
        editPlayerRequestDTO.setSurname("Doe");
        editPlayerRequestDTO.setType(UserType.ADMIN);
        editPlayerRequestDTO.setUserID(1);
        editPlayerRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        when(userService.GetAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.GetAllUsers()).thenReturn(userList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");

        User user1 = new User();
        user1.setPassword("iloveyou");
        user1.setType(UserType.PLAYER);
        user1.setUserID(1);
        user1.setUsername("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user);
        when(userService.GetAllUsers()).thenReturn(userList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/");
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.GetUser(anyInt())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
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
        when(userService.GetUser(anyInt())).thenReturn(player);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
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
        Player player = new Player();
        player.setBalance(10.0d);
        player.setEmail("?");
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
        when(userService.GetUser(anyInt())).thenReturn(player);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/{id}", 1);
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.UpdateUser((User) any(), anyInt())).thenReturn(user);

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
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        when(userService.UpdateUser((User) any(), anyInt())).thenReturn(player);

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
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.UpdateUser((User) any(), anyInt())).thenReturn(user);

        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("?");
        editPlayerRequestDTO.setName("Name");
        editPlayerRequestDTO.setNationalIDNumber("42");
        editPlayerRequestDTO.setPassword("iloveyou");
        editPlayerRequestDTO.setPhoneNumber("4105551212");
        editPlayerRequestDTO.setSurname("Doe");
        editPlayerRequestDTO.setType(UserType.PLAYER);
        editPlayerRequestDTO.setUserID(1);
        editPlayerRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.UpdateUser((User) any(), anyInt())).thenReturn(user);

        EditPlayerRequestDTO editPlayerRequestDTO = new EditPlayerRequestDTO();
        editPlayerRequestDTO.setEmail("jane.doe@example.org");
        editPlayerRequestDTO.setName("Name");
        editPlayerRequestDTO.setNationalIDNumber("42");
        editPlayerRequestDTO.setPassword("iloveyou");
        editPlayerRequestDTO.setPhoneNumber("4105551212");
        editPlayerRequestDTO.setSurname("Doe");
        editPlayerRequestDTO.setType(UserType.ADMIN);
        editPlayerRequestDTO.setUserID(1);
        editPlayerRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        Player player = new Player();
        player.setBalance(10.0d);
        player.setEmail("?");
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
        when(userService.UpdateUser((User) any(), anyInt())).thenReturn(player);

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
        String content = (new ObjectMapper()).writeValueAsString(editPlayerRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        doNothing().when(userService).DeleteUser(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/{id}", 1);
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
        doNothing().when(userService).DeleteUser(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/user/{id}", 1);
        deleteResult.characterEncoding("Encoding");
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
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), (String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
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

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player);
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), (String) any())).thenReturn(playerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
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

        Player player1 = new Player();
        player1.setBalance(10.0d);
        player1.setEmail("jane.doe@example.org");
        player1.setFundsLost(10.0d);
        player1.setFundsPayedOut(10.0d);
        player1.setName("?");
        player1.setNationalIDNumber("42");
        player1.setPassword("iloveyou");
        player1.setPhoneNumber("4105551212");
        player1.setSurname("Doe");
        player1.setType(UserType.PLAYER);
        player1.setUserID(1);
        player1.setUsername("janedoe");
        player1.setWinCoefficient(10.0d);

        ArrayList<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player);
        when(userService.GetAllUsersPagged(anyInt(), anyInt(), (String) any())).thenReturn(playerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/pagged/{pageNo}/{pageSize}/{sortBy}", 1, 3, "Sort By");
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

