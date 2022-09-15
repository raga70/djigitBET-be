package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Entity.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements IUserService{
   
   @Autowired
   private IUserRepository userRepository;

    @Override
    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User SaveUser(User user) {
       return userRepository.save(user);
    }

}
