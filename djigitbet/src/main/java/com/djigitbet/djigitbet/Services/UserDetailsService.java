package com.djigitbet.djigitbet.Services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    public UserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.djigitbet.djigitbet.Model.Entity.User user = userService.GetUser(username);

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true,
                true, true, user.getAuthorities());
    }

}
