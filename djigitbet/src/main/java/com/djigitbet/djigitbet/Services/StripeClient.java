package com.djigitbet.djigitbet.Services;

import com.djigitbet.djigitbet.Model.Entity.Player;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {
    @Autowired
    StripeClient(@Value("${stripe.secret}")
                 String stripeSecret) {
        Stripe.apiKey = stripeSecret;
    }

    public Customer createCustomer(String token, String email) throws Exception {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("email", email);
        customerParams.put("source", token);
        return Customer.create(customerParams);
    }

    private Customer getCustomer(String id) throws Exception {
        return Customer.retrieve(id);
    }

    public Charge chargeNewCard(String token, double amount, Player incomingPlayer) throws Exception {
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "eur");
        chargeParams.put("source", token);
        String descStr = "DjigitBET- " + amount + "eur" + "  FOR: username: " + incomingPlayer.getUsername() + "  userID: " + incomingPlayer.getUserID() + "  name:" + incomingPlayer.getName() + " " + incomingPlayer.getSurname();
        chargeParams.put("description", descStr);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }

    public Charge chargeCustomerCard(String customerId, int amount) throws Exception {
        String sourceCard = getCustomer(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put("source", sourceCard);
        Charge charge = Charge.create(chargeParams);
        return charge;
    }
}