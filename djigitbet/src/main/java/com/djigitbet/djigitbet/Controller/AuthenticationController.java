package com.djigitbet.djigitbet.Controller;

import com.djigitbet.djigitbet.Entity.impl.Player;
import com.djigitbet.djigitbet.Entity.impl.User;
import com.djigitbet.djigitbet.Entity.impl.UserType;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.djigitbet.djigitbet.security.domain.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/authenticate")
public class AuthenticationController {
    
    @Autowired
    UserService userService;
  //  AuthenticationManager authenticationManager = new AuthenticationManager();
//    @PostMapping("/login")
//  public boolean Login(@RequestBody String username, String password){
//        return authenticationManager.Login(username, password);
//     
//      //return "login success";
//  }

    @PostMapping("/register")
    public String SaveUser(@Valid @RequestBody() Player incomingUser){

        if(incomingUser.getType()== UserType.ADMIN){  //java is a shitty language and doesn't support object recasting
             // User user = (User) incomingUser;
            //userService.SaveUser(user); 
            return "Not enough permissions to add an admin";
        }
        else{
            
           // authenticationManager.Register(incomingUser);
            return "the player has been added";
        }
    }

   @Autowired // this piece of shit doesn't work 
    private AuthenticationManagerUserService authenticationManagerUserService ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@Valid @RequestBody LoginCredentials loginRequest) {

        UsernamePasswordAuthenticationToken loginCredentials =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword());

//        Authentication authentication =
//                authenticationManager.authenticate(loginCredentials);
//
//        User user = (User) authentication.getPrincipal();
        
        if(authenticationManagerUserService.Login(loginRequest.getUsername(), loginRequest.getPassword())){
            String jwtToken = jwtUtil.createJWT(userService.GetUser(loginRequest.getUsername()));
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                    .header("ROLE", userService.GetUser(loginRequest.getUsername()).getType().toString())
                    
                    .build();
        }
        else{
            return ResponseEntity
                    .badRequest()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + "invalid")
                    .build();
        }
        
        
        
        
//        
//        String jwtToken = jwtUtil.createJWT(user);
//
//        return ResponseEntity
//                .ok()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
//                .build();
//
//    }
}}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

