package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class FullContractDto {

    private String id;

    private String userId;

    private TariffDto tariffDto;

    private String phoneNumber;

    private List<OptionsDto> contractOptions;

}