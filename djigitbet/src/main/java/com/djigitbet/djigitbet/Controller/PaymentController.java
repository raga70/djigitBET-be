package com.djigitbet.djigitbet.Controller;

import com.djigitbet.djigitbet.Model.DTO.paymentDTO;
import com.djigitbet.djigitbet.Model.Entity.Player;
import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.Model.Entity.UserType;
import com.djigitbet.djigitbet.Services.StripeClient;
import com.djigitbet.djigitbet.Services.UserService;
import com.djigitbet.djigitbet.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController

@RequestMapping("/payment")
public class PaymentController {

    private final StripeClient stripeClient;
    @Value("${stripe.secret}")
    String stripeSecret;
    @Autowired
    UserService userService;
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }


//    @PostMapping("/charge")
//    public Charge chargeCard(@RequestHeader(value="token") String token, @RequestHeader(value="amount") Double amount) throws Exception {
//        return this.stripeClient.chargeNewCard(token, amount);
//    }

//        @PostMapping("/charge")
//    public Charge chargeCard(@RequestBody paymentDTO payment) throws Exception {
//        var a = this.stripeClient.chargeNewCard(payment.getId(), payment.getAmount());
//        return a;
//    }


    @PostMapping("/")
        //i am reusing the dto which only validates a number
    ResponseEntity<?> PlacePayment(@RequestHeader("Authorization") String token, @RequestBody paymentDTO incomingPayment) {
        Player player;

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


            var resp = this.stripeClient.chargeNewCard(incomingPayment.getId(), incomingPayment.getAmount(), player);
            if (!resp.getStatus().equals("succeeded") || !resp.getPaid()) {

                return ResponseEntity.badRequest().body("Something went wrong");
            } else {
                player.setBalance(player.getBalance() + incomingPayment.getAmount());
                userService.SaveUser(player);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("payment went through");

    }


}
