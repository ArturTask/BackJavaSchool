package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionsDto {

    private String id;

    private String name;

    private String optionType;

    private String cost;

    private String tariffId;

}
