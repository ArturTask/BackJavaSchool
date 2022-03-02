package ru.javaSchoolProject.dto;

import lombok.Data;

@Data
public class RegDto {

    public RegDto(String token, String role) {
        this.token = token;
        this.role = role;
    }

    private String token;

    private String role;
}
