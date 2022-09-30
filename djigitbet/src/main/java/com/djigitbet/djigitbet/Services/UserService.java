package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {
   
   @Autowired
   private IUserRepository userRepository;


    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }
    
    
    public User GetUser(int id) {
        return userRepository.findById(id).get();
    }

    
    public User GetUser(String username) {
        return userRepository.findByUsername(username).get(0);
    }
    
    
     
    public User SaveUser(User user) {
       return userRepository.save(user);
    }
    
    
     
    public User UpdateUser(User newUser, int id) {
       //update the user by id
       userRepository.save(newUser);
       return userRepository.findById(id).get();


        
        
    }
    
    
    
     
    public void DeleteUser(int id){
        userRepository.deleteById(id);
    }
    

}
