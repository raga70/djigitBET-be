package com.djigitbet.djigitbet.Controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
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

@ContextConfiguration(classes = {UnprivilegedUserController.class})
@ExtendWith(SpringExtension.class)
class UnprivilegedUserControllerTest {
    @MockBean
    private JWTUtil jWTUtil;

    @Autowired
    private UnprivilegedUserController unprivilegedUserController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UnprivilegedUserController#UpdateUser(String, EditPlayerRequestDTO)}
     */
    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.PLAYER);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.GetUser(anyInt())).thenReturn(user);

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/unpriviligeduser/")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(unprivilegedUserController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not authorized to update this user"));
    }

    /**
     * Method under test: {@link UnprivilegedUserController#UpdateUser(String, EditPlayerRequestDTO)}
     */
    @Test
    void testUpdateUser2() throws Exception {
        User user = new User();
        user.setPassword("iloveyou");
        user.setType(UserType.ADMIN);
        user.setUserID(1);
        user.setUsername("janedoe");
        when(userService.GetUser(anyInt())).thenReturn(user);

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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/unpriviligeduser/")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(unprivilegedUserController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("You are not allowed to change your type"));
    }
}

