package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TariffDto {

    private Integer id;

    private String title;

    private String description;

    private String cost;

    private List<OptionsDto> options;

}
