package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractIdAndNumberDto {

    private String contractId;

    private String phoneNumber;
}
