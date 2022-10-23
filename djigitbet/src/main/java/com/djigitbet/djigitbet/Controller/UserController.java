package com.djigitbet.djigitbet.Controller;

import com.djigitbet.djigitbet.Model.DTO.*;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validation;
import java.util.Arrays;
import java.util.List;


@RestController

@RequestMapping("/user")

public class UserController {

    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private UserService userService;


    @PostMapping("/")
    public String SaveUser( @RequestBody  EditPlayerRequestDTO incomingDTO) {
        
       if(!validationWorkaraound(incomingDTO)){
           return "Validation failed";}

       
          
        
        Player incomingUser = new Player(incomingDTO);
        


        if (incomingUser.getType() == UserType.ADMIN) {   //recast because java can`t detect the type of the object 
            User user = incomingUser;
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
    PlayerDTO UpdateUser(@RequestBody  EditPlayerRequestDTO incomingDTO, @PathVariable int id) {
        if(!validationWorkaraound(incomingDTO)){
            return null;}
        Player incomingUser = new Player(incomingDTO);
        if (incomingUser.getType() == UserType.ADMIN) {
            User user = incomingUser;
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


    @GetMapping("/pagged/{pageNo}/{pageSize}/{sortBy}")
    public List<PlayerFullDTO> GetAllPlayersPagged(@PathVariable int pageNo, @PathVariable int pageSize, @PathVariable String sortBy) {

        var outgoing = modelMapper.map(userService.GetAllUsersPagged(pageNo, pageSize, sortBy), PlayerFullDTO[].class);
        return Arrays.stream(outgoing).toList();

    }
    
    
    
    
    
    private boolean validationWorkaraound(EditPlayerRequestDTO playerDTO){
        if(playerDTO.getType().equals(UserType.ADMIN)){
            EditUserRequestDTO userDTO = new EditUserRequestDTO(playerDTO);
            var validator = Validation.buildDefaultValidatorFactory().getValidator();
            var violations = validator.validate(userDTO);
            if(violations.size() > 0){
                return false;
            }
            return true;
        }
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(playerDTO);
        if(violations.size() > 0){
            return false;
        }
        return true;
    }

}           
    
    
    
    
