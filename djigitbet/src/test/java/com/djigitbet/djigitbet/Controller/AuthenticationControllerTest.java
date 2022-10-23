package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * Method under test: {@link AuthenticationController#SaveUser(EditPlayerRequestDTO)}
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Not enough permissions to add an admin"));
    }
}

