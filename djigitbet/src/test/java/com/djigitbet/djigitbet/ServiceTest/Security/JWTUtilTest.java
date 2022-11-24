package com.djigitbet.djigitbet.ServiceTest.Security;

import com.djigitbet.djigitbet.Model.Entity.User;
import com.djigitbet.djigitbet.security.JWTUtil;
import com.stripe.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JWTUtilTest {
    private static final String CLAIM_USERNAME_KEY = "username";
    private static final String CLAIM_USERID_KEY = "userID";
    private String issuer;
    private String secret = "ej7+D6Hh2r2KBEIuoGz1RGjtXooObzebSSxNm7yu2YSkEKv8aQmC2iYuxXLkKYKgT1210e5ZKMoY4XM0uQnkJkKGA01tBWZK+Pi3zX+nZvVNTJbZwRSOyZ2bm99exCq+z6ER5q5XWes1G9/sG5rp7kK9bL/NdNdU13HAFjJew6NwyxIxFH5VhXWwtgIJy+1KcJ9+3kq6uYNylrPC2U1h0xvR9qQzi1D2m9H8ZA==";
    private String audience = "React App";
    private long timeToLiveInSeconds = 1000;
    private SecretKey secretKey;
    private JWTUtil jwtUtil;
    private Claims claims;
    private Jwt jwt; 
    
    @BeforeEach
    public void setUp(){
        claims = Mockito.mock(Claims.class);
        jwt = Mockito.mock(Jwt.class);
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtUtil = new JWTUtil(issuer,secret,audience,timeToLiveInSeconds);
    }
    
    @Test
    public void setUpSecretKey_pass(){
        assertNotNull(secretKey);
    }
    
    @Test
    public void createJWT_pass(){
        User user = User.builder().username("test").password("test").build();
        jwtUtil.setUpSecretKey();
        assertNotNull(jwtUtil.createJWT(user));
    }
    
   //parse jtw is a problem considering that token eventually expires
    
}
