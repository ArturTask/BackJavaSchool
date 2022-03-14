package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
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