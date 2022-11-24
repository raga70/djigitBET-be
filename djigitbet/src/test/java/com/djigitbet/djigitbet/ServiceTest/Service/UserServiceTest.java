package com.djigitbet.djigitbet.ServiceTest.Service;

import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
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
        List<User> list = new ArrayList<>();
        list.add(User.builder().userID(1).build());
        list.add(User.builder().userID(2).build());
        Mockito.when(userRepositoryMock.findAll()).thenReturn(list);
    }
    
    @Test
    public void getAllPaged(){
        Mockito.when(userRepositoryMock.findAllByType(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());
        assertNotNull(userService.GetAllUsersPagged(1, 1, "test"));
    }
    
    @Test
    public void getUser_pass(){
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(User.builder().userID(1).build()));
        assertNotNull(userService.GetUser(1));
    }
    
    @Test
    public void getUser_fail(){
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        assertNull(userService.GetUser(1));
    }
    
    @Test
    public void saveUser_pass(){
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(User.builder().userID(1).build());
        assertNotNull(userService.SaveUser(User.builder().userID(1).build()));
    }
    
    @Test
    public void saveUser_fail(){
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(null);
        assertNull(userService.SaveUser(User.builder().userID(1).build()));
    }
    
    @Test
    public void updateUser_pass(){
        User user = User.builder().userID(1).password("password").build();
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(user);
        assertNotNull(userService.UpdateUser(user, 1));
    }
    
    @Test
    public void updateUser_fail(){
        User user = User.builder().userID(1).password("password").build();
        Mockito.when(userRepositoryMock.save(Mockito.any())).thenReturn(null);
        assertNull(userService.UpdateUser(user, 1));
    }
    
    @Test
    public void deleteUser_pass(){
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(User.builder().userID(1).build()));
        Mockito.doNothing().when(userRepositoryMock).delete(Mockito.any());
    }
    
    @Test
    public void deleteUser_fail(){
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.empty());
        Mockito.doNothing().when(userRepositoryMock).delete(Mockito.any());
    }
}
