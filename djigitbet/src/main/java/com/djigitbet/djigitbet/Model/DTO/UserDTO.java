package com.djigitbet.djigitbet.Model.DTO;

import com.djigitbet.djigitbet.Model.Entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private int userID;
    private UserType type;
    private String username;
}
    



