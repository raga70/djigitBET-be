package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.DataAcessLayer.IUserRepository;
import com.djigitbet.djigitbet.Entity.impl.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationManagerUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;
    
    public  boolean Login(String username, String password){

       User user = userRepository.findByUsername(username).get(0);
      
      ///testblock
       var a =  user.getPassword();
       var b = password;
       boolean result;
       if(a.equals(b)){ //lol put more money in to sailing yachts and f1 teams no need to implement == for string comparison  Oracle ❤️
              result = true;
         }else result = false;
      ///testblock
      
      
         if(user != null){
             if(Objects.equals(password, user.getPassword())){ //update pass (hash)
                 user.setPassword(passwordEncoder.encode(user.getPassword()));
                 userRepository.save(user);
                 return true;
             }
              if(passwordEncoder.matches(password, user.getPassword())){
                return true;
              }
         }
         return false;
    }
    
     
    public boolean Register(User user ){
        if(userRepository.findByUsername(user.getUsername()).size() == 0){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
    
         
    
}
