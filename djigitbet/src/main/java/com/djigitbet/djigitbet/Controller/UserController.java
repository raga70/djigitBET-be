package com.djigitbet.djigitbet.Controller;


import com.djigitbet.djigitbet.Entity.impl.Player;
import com.djigitbet.djigitbet.Entity.impl.User;
import com.djigitbet.djigitbet.Entity.impl.UserType;
import com.djigitbet.djigitbet.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
    
  //  private IUserService userService = new UserService();
    
   @Autowired
    private UserService userService; 
    
    
    
    @PostMapping("/save")
    public String SaveUser(@Valid @RequestBody()  Player incomingUser){
      
        if(incomingUser.getType()== UserType.ADMIN){  //java is a shitty language and doesn't support object recasting
            User user = (User) incomingUser;
            userService.SaveUser(user); return "the user has been added";
        }
        else{
             userService.SaveUser(incomingUser);
             return "the player has been added";
        }
    }
    
    @GetMapping("/getall")
    public List<User> GetAllUsers(){
        var a = userService.GetAllUsers();
        int i = 0;
        return a;
    }
    
    
    @GetMapping("/get/{id}")
    public User GetUser(@PathVariable int id){
        return userService.GetUser(id);
    }
    
    @CrossOrigin(origins = "*")
    @PutMapping("/update/{id}")
    User UpdateUser(@RequestBody Player incomingUser, @PathVariable int id) {

        if(incomingUser.getType()== UserType.ADMIN){  //java is a shitty language and doesn't support object recasting
            User user = (User) incomingUser;
            return userService.UpdateUser(user, id);

 

          //  userService.SaveUser(user); return "the user has been added";
        }
        else{
            //userService.SaveUser(incomingUser);
            return userService.UpdateUser(incomingUser, id);
            //return "the player has been added";
        }
      
      
      
      
    }
    
    @DeleteMapping("/delete/{id}")
    void DeleteUser(@PathVariable int id) {
        userService.DeleteUser(id);
    }
    
}
