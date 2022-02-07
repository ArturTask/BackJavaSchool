package ru.javaSchoolProject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInDto {

    public LogInDto(String token, String id, String role) {
        this.token = token;
        this.id = id;
        this.role = role;
    }

    private String token;

    private String id;

    private String role;

}
