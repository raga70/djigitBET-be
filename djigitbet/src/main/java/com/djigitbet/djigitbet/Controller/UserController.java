package com.djigitbet.djigitbet.Controller;


import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.PlayerDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import javax.validation.Valid;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.List;

 //TODO: Do validation @valid
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/user")

public class UserController {

     private ModelMapper modelMapper = new ModelMapper();

     @Autowired
     private UserService userService;


     @PostMapping("/")
     public String SaveUser(@Valid @RequestBody() EditPlayerRequestDTO incomingDTO) {


         Player incomingUser = new Player(incomingDTO);


         if (incomingUser.getType() == UserType.ADMIN) {  //java is a shitty language and doesn't support object recasting
             User user = (User) incomingUser;
             userService.SaveUser(user);
             return "the user has been added";
         } else {
             userService.SaveUser(incomingUser);
             return "the player has been added";
         }
     }

     @GetMapping("/")
     public List<PlayerDTO> GetAllUsers(HttpServletResponse response) {

         var outgoing = modelMapper.map(userService.GetAllUsers(), PlayerDTO[].class);
         return Arrays.stream(outgoing).toList();

     }


     @GetMapping("/{id}")
     public PlayerDTO GetUser(@PathVariable int id) {

         var outgoing = modelMapper.map(userService.GetUser(id), PlayerDTO.class);
         return outgoing;
     }


     @PutMapping("/{id}")
     PlayerDTO UpdateUser(@RequestBody EditPlayerRequestDTO incomingDTO, @PathVariable int id) {

         Player incomingUser = new Player(incomingDTO);
         if (incomingUser.getType() == UserType.ADMIN) {  //java is a shitty language and doesn't support object recasting
             User user = (User) incomingUser;
             var outgoing = modelMapper.map(userService.UpdateUser(user, id), PlayerDTO.class);
             return outgoing;

             
         } else {
             var outgoing = modelMapper.map(userService.UpdateUser(incomingUser, id), PlayerDTO.class);
             return outgoing;
         }


     }

     @DeleteMapping("/{id}")
     void DeleteUser(@PathVariable int id) {
         userService.DeleteUser(id);
     }


  

    
 }           
    
    
    
    
