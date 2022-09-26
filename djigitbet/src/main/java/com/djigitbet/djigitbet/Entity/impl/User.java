package com.djigitbet.djigitbet.Entity.impl;


import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
//@Table(indexes = {
//        @Index(name = "index1", columnList = "userID"),
//        @Index(name = "index2", columnList = "username")
//})
@Inheritance(strategy = InheritanceType.JOINED)

public  class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;



//    @Builder.Default
//    private String uuid = UUID.randomUUID().toString();    //remove maybe
//    

    @NotBlank(message = "type is mandatory")
   // @Enumerated(EnumType.STRING)
    private UserType type; //required so i can define what kind of object need to be created after the json file has been received by the controller

    @Column(unique=true)
    private String username;
    private String password;

    private boolean enabled;
    
    
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }

  
    public void setPassword(String password) {
        this.password = password;
    }

 
    public int getUserID() {
        return userID;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(this.type.name()));
        return authorities;
    }




}
