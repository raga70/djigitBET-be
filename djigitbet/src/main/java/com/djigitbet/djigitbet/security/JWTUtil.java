package com.djigitbet.djigitbet.security;


import com.djigitbet.djigitbet.Entity.impl.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;

import java.util.Date;
import java.util.UUID;

@Component
@Log4j2

public  class JWTUtil {

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.ttl-in-seconds}")
    private long timeToLiveInSeconds;

    public JWTUtil(@Value("${jwt.issuer}") String issuer, //it`s just the small stuff like that take 30 min to debug because spring removed a feature Make me Hate this language and framework 
                   @Value("${jwt.secret}") String secret,
                   @Value("${jwt.audience}") String audience,
                   @Value("${jwt.ttl-in-seconds}") long timeToLiveInSeconds) {
        this.issuer = issuer;
        this.secret = secret;
        this.audience = audience;
        this.timeToLiveInSeconds = timeToLiveInSeconds;
    }
    
    
    
    
    private SecretKey secretKey;

    @PostConstruct
    public void setUpSecretKey() {
        try {
            secretKey = Keys.hmacShaKeyFor(secret.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Error generating JWT Secret Key : {}", e.getMessage());
            throw new RuntimeException("Error generating JWT Secret Key", e);
        }
    }


    private static final String CLAIM_USERNAME_KEY = "username";
    private static final String CLAIM_USERID_KEY = "userID";


    public String createJWT(User user) {

        String jwt =
                Jwts.builder()
                        .setId(UUID.randomUUID().toString())
                        .setSubject(user.getUsername())
                        .setIssuer(issuer)
                        .setIssuedAt(Date.from(Instant.now()))
                        .setExpiration(Date.from(Instant.now().plus(Duration.ofSeconds(timeToLiveInSeconds))))
                        .claim(CLAIM_USERNAME_KEY, user.getUsername())
                        .claim(CLAIM_USERID_KEY, user.getUserID())
                        .signWith(secretKey)
                        .compact();
        return jwt;
    }



    public Claims parseJWT(String jwtString) {

        Jws<Claims> headerClaimsJwt =
                Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwtString);

        Claims claims = headerClaimsJwt.getBody();

        return claims;
    }
}
