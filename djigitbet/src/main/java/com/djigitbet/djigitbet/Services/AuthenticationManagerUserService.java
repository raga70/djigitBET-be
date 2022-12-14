package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Repositories.IUserRepository;
import com.djigitbet.djigitbet.Model.Entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationManagerUserService {
    private PasswordEncoder passwordEncoder;

    private IUserRepository userRepository;
    
    public AuthenticationManagerUserService(PasswordEncoder encoder, IUserRepository userRepository) {
        this.passwordEncoder = encoder;
        this.userRepository = userRepository;
    }
        
    public boolean Login(String username, String password) {

        User user = userRepository.findByUsername(username).get(0);

        if (user != null) {
            if (Objects.equals(password, user.getPassword())) { //update pass (hash)
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return true;
            }
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
    

    public boolean Register(User user) {
        if (userRepository.findByUsername(user.getUsername()).size() == 0) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }


}
