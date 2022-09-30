package com.djigitbet.djigitbet.Controller;

import com.auth0.jwt.interfaces.Header;
import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.PlayerDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.AuthenticationManagerUserService;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.djigitbet.djigitbet.security.Entity.LoginCredentials;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/authenticate")
public class AuthenticationController {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    UserService userService;


    @PostMapping("/register")
    public String SaveUser(@Valid @RequestBody() EditPlayerRequestDTO incomingDTO){
        Player incomingUser = new  Player(incomingDTO);
        
        if(incomingUser.getType()== UserType.ADMIN){  //java is a shitty language and doesn't support object recasting
       
            return "Not enough permissions to add an admin";
        }
        else{
                
            userService.SaveUser(incomingUser); //TODO: DO VALIDATION
            return "the player has been added";
        }
    }

   @Autowired // this piece of shit doesn't work 
    private AuthenticationManagerUserService authenticationManagerUserService ;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;
  //  @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@Valid @RequestBody LoginCredentials loginRequest) {

        UsernamePasswordAuthenticationToken loginCredentials =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword());
        
        if(authenticationManagerUserService.Login(loginRequest.getUsername(), loginRequest.getPassword())){
            String jwtToken = jwtUtil.createJWT(userService.GetUser(loginRequest.getUsername()));
            var outgoing = modelMapper.map(userService.GetUser(loginRequest.getUsername()), PlayerDTO.class);
            
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.AUTHORIZATION,  jwtToken)
                    .header("ROLE", userService.GetUser(loginRequest.getUsername()).getType().toString())
                    .header("Access-Control-Expose-Headers", "AUTHORIZATION")
                    .header("Access-Control-Expose-Headers", "ROLE")
                    .body(outgoing);
                    //.build();
                    
        }
        else{
   
            return ResponseEntity
                    .badRequest()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + "invalid")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    
                    .build();
        }
        

}}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

