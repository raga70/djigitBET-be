package com.djigitbet.djigitbet.ServiceTest.Service;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.UserDetailsService;
import com.djigitbet.djigitbet.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserDetailsServiceTest {
    private UserService userService;
    private UserDetailsService userDetailsService;
    private User user;
    @BeforeEach
    public void setUp(){
        userService =  mock(UserService.class);
        user = mock(User.class);
        userDetailsService = new UserDetailsService(userService);
    }
    
    @Test
    public void loadUserByUsername_pass(){
        //Act
        Mockito.when(userService.GetUser(Mockito.any()))
                .thenReturn(User.builder().username("test").type(UserType.PLAYER).userID(1).password("pw").build());
        
        //Assert
        assertNotNull(userDetailsService.loadUserByUsername("test"));
    }
}
