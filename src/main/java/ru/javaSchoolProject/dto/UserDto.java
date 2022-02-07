package ru.javaSchoolProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {


    private Integer id;

    private String login;

    private String password;

    private String role;



    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}