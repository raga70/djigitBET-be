package com.djigitbet.djigitbet.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.djigitbet.djigitbet.Entity.impl.User user = userService.GetUser(username);

        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true,
                true, true, user.getAuthorities());
    }

}
