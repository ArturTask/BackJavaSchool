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
public class ContractDto {

    private String userId;

    private String tariffId;

    private String phoneNumber;

    private List<OptionsDto> contractOptions;

}
