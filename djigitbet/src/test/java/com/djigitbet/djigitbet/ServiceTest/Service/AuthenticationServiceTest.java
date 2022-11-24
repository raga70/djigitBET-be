package com.djigitbet.djigitbet.ServiceTest.Service;


import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.EditUserRequestDTO;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import org.apache.tomcat.util.buf.UEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {
    String username = "username";
    String password = "password";
    private AuthenticationManagerUserService authenticationManagerUserService;
    private PasswordEncoder passwordEncoder;
    private IUserRepository userRepositoryMock;
    
    @BeforeEach
    public void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userRepositoryMock = Mockito.mock(IUserRepository.class);
        authenticationManagerUserService = new AuthenticationManagerUserService(passwordEncoder, userRepositoryMock);
    }
    
    @Test
    public void login_pass() {
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepositoryMock.findByUsername(Mockito.any())).thenReturn(users);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertTrue(authenticationManagerUserService.Login(username, password));
    }
    
    @Test
    public void login_fail_1(){
        User user = User.builder().password("wrongPassword").username("wrongUsername").build();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepositoryMock.findByUsername(username)).thenReturn(users);
        assertFalse(authenticationManagerUserService.Login(username,password));
    }
    
    @Test
    public void login_fail_2(){
        User user = User.builder().password("incorrectPassword").username(username).build();
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepositoryMock.findByUsername(username)).thenReturn(users);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        assertFalse(authenticationManagerUserService.Login(username, password));
    }
    
    @Test
    public void register_pass(){
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        users.add(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        assertTrue(authenticationManagerUserService.Register(user));
    }
    
    @Test
    public void register_fail(){
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        assertTrue(authenticationManagerUserService.Register(user));
    }
}
