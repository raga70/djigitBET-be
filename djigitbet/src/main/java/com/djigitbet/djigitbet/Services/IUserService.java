package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Entity.impl.User;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IUserService {

    public User SaveUser (User user);
    public List<User> GetAllUsers();
}
