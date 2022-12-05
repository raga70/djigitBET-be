package com.djigitbet.djigitbet.ServiceTest.Service;


import com.djigitbet.djigitbet.Repositories.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
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
        //Arrange
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        
        //Act
        users.add(user);
        
        //Assert
        Mockito.when(userRepositoryMock.findByUsername(Mockito.any())).thenReturn(users);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
        assertTrue(authenticationManagerUserService.Login(username, password));
    }
    
    @Test
    public void login_fail_1(){
        //Arrange
        User user = User.builder().password("wrongPassword").username("wrongUsername").build();
        List<User> users = new ArrayList<>();
        
        //Act
        users.add(user);
        
        //Assert
        Mockito.when(userRepositoryMock.findByUsername(username)).thenReturn(users);
        assertFalse(authenticationManagerUserService.Login(username,password));
    }
    
    @Test
    public void login_fail_2(){
        //Arrange
        User user = User.builder().password("incorrectPassword").username(username).build();
        List<User> users = new ArrayList<>();
        
        //Act
        users.add(user);
        
        //Assert
        Mockito.when(userRepositoryMock.findByUsername(username)).thenReturn(users);
        Mockito.when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(false);
        assertFalse(authenticationManagerUserService.Login(username, password));
    }
    
    @Test
    public void register_pass(){
        //Arrange
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        
        //Act
        users.add(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        //Assert
        assertTrue(authenticationManagerUserService.Register(user));
    }
    
    @Test
    public void register_fail(){
        //Arrange
        User user = User.builder().password(password).username(username).build();
        List<User> users = new ArrayList<>();
        
        //Assert
        assertTrue(authenticationManagerUserService.Register(user));
    }
}
