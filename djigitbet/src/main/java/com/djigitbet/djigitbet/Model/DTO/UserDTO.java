package com.djigitbet.djigitbet.Model.DTO;

import com.djigitbet.djigitbet.Model.Entity.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
   
    private int userID;
    private UserType type;
    private String username;
}
    



