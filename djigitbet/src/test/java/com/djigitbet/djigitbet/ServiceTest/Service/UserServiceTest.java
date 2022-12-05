package com.djigitbet.djigitbet.ServiceTest.Service;

import com.djigitbet.djigitbet.Repositories.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserServiceTest {
    private IUserRepository userRepositoryMock;
    private UserService userService;
    
    @BeforeEach
    public void setUp(){
        userRepositoryMock = mock(IUserRepository.class);
        userService = new UserService(userRepositoryMock);
    }
    
    @Test
    public void getAll_pass(){
        //Arrange
        List<User> list = new ArrayList<>();
        
        //Act
        list.add(User.builder().userID(1).build());
        list.add(User.builder().userID(2).build());
        Mockito.when(userRepositoryMock.findAll()).thenReturn(list);
        
        //Assert
        assertNotNull(userService.GetAllUsers());
        assertTrue(userService.GetAllUsers().size() == 2);
    }
    
    @Test
    public void getAllPaged(){
        //Act
        Mockito.when(userRepositoryMock.findAllByType(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());
        
        //Assert
        assertNotNull(userService.GetAllUsersPagged(1, 1, "test"));
    }
    
    @Test
    public void getUser_pass(){
        //Act
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(User.builder().userID(1).build()));
        
        //Assert
        assertNotNull(userService.GetUser(1));
    }
    
    @Test
    public void getUser_fail(){
        //Act
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        
        //Assert
        assertNull(userService.GetUser(1));
    }
    
    @Test
    public void saveUser_pass(){
        //Act
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(User.builder().userID(1).build());
        
        //Assert
        assertNotNull(userService.SaveUser(User.builder().userID(1).build()));
    }
    
    @Test
    public void saveUser_fail(){
        //Act
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(null);
        
        //Assert
        assertNull(userService.SaveUser(User.builder().userID(1).build()));
    }
    
    @Test
    public void updateUser_pass(){
        //Arrange
        User user = User.builder().userID(1).password("password").build();
        
        //Act
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(user);
        
        //Assert
        assertNotNull(userService.UpdateUser(user, 1));
    }
    
    @Test
    public void updateUser_fail(){
        //Arrange
        User user = User.builder().userID(1).password("password").build();
        
        //Act
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(null);
        
        //Assert
        assertNull(userService.UpdateUser(user, 1));
    }
    
    @Test
    public void deleteUser_pass(){
        //Act
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        Mockito.doNothing().when(userRepositoryMock).delete(Mockito.any());
        
    }
}
