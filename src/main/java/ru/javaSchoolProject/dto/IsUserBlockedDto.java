package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsUserBlockedDto {

    private String message;

    private boolean isBlocked;
}
