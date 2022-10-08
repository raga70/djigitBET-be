package com.djigitbet.djigitbet.Controller;

import com.djigitbet.djigitbet.Model.DTO.EditPlayerRequestDTO;
import com.djigitbet.djigitbet.Model.DTO.SlotCalculationsDTO;
import com.djigitbet.djigitbet.Model.DTO.placeBetDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.SlotEngine;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/slots")
public class SlotsController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    UserService userService;

    @Autowired
    SlotEngine slotEngine;
    
    
    
    
    


    @PostMapping("/")
    ResponseEntity<?> UpdateUser(@RequestHeader("Authorization") String token, @RequestBody placeBetDTO bet ) {
        Player player;
        SlotCalculationsDTO slotCalculationsDTO = new SlotCalculationsDTO();
        int userIDFromToken = 0;
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);

            userIDFromToken = jwtUtil.parseJWT(jwtToken).get("userID", Integer.class);
        } else {
            return ResponseEntity.badRequest().body("You are not authorized");
        }

        User tmpUser = userService.GetUser(userIDFromToken);
        if (tmpUser.getType() != UserType.PLAYER) {
            return ResponseEntity.badRequest().body("You are not allowed to play");
        }
        player = (Player) tmpUser;
        try {
            slotCalculationsDTO = slotEngine.Play(player, bet.getBetAmount());
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body(slotCalculationsDTO);

    }
}