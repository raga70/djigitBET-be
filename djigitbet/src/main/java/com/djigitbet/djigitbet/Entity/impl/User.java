package com.djigitbet.djigitbet.Entity.impl;


import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Type;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public  class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userID;

    @NotBlank(message = "type is mandatory")
    
    private UserType type; //required so i can define what kind of object need to be created after the json file has been received by the controller
    private String username;
    private String password;


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
}
