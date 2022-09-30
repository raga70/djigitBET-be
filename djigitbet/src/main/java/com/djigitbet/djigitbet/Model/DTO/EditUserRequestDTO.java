package com.djigitbet.djigitbet.Model.DTO;

import com.djigitbet.djigitbet.Model.Entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditUserRequestDTO {

    private int userID;
    
    @NotBlank
    private UserType type;
    @NotBlank
    private String username;
    
    private String password;
}
