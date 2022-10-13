package com.djigitbet.djigitbet.Controller;


import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.PlayerDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/unpriviligeduser")
public class UnprivilegedUserController {


    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    @PutMapping("/")
    ResponseEntity<?> UpdateUser(@RequestHeader("Authorization") String token, @RequestBody @Validated EditPlayerRequestDTO incomingDTO) {
        User responceUser;
        int userIDFromToken = 0;
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            userIDFromToken = jwtUtil.parseJWT(jwtToken).get("userID", Integer.class);

        }
        if (userService.GetUser(userIDFromToken).getType() != incomingDTO.getType()) {
            return ResponseEntity.badRequest().body("You are not allowed to change your type");
        }
        if (userIDFromToken == incomingDTO.getUserID()) {
            Player incomingUser = new Player(incomingDTO);
            if (incomingUser.getType() == UserType.ADMIN) {
                User user = incomingUser;

                responceUser = userService.UpdateUser(user, incomingDTO.getUserID());
            } else {
                incomingUser.setBalance(((Player) userService.GetUser(incomingUser.getUserID())).getBalance()); //JPA NO Joke the documentation recommends this (Just pull it from the database and set it yourself)
                incomingUser.setWinCoefficient(((Player) userService.GetUser(incomingUser.getUserID())).getWinCoefficient());
                incomingUser.setFundsLost(((Player) userService.GetUser(incomingUser.getUserID())).getFundsLost());
                incomingUser.setFundsPayedOut(((Player) userService.GetUser(incomingUser.getUserID())).getFundsPayedOut());
                responceUser = userService.UpdateUser(incomingUser, incomingDTO.getUserID());
            }
        } else {
            return ResponseEntity.badRequest().body("You are not authorized to update this user");

        }
        var outgoing = modelMapper.map(responceUser, PlayerDTO.class);
        return ResponseEntity
                .ok()
                .body(outgoing);
    }/// if you are wondering why i am doing this: the jwt token is signed with HMACSHA512 signature 
    /// my filtering chain will not allow a forged jwt 
    /// but why: the user can easily modify the request and slap the id of another user in there or try to change his user type
}
