package ru.javaSchoolProject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TariffAnswerDto {

    public TariffAnswerDto(String response) {
        this.response = response;
    }

    private String response;
}
