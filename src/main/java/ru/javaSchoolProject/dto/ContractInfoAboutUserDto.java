package ru.javaSchoolProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractInfoAboutUserDto {

    private String contractId;

    private String phoneNumber;

    private String userId;

    private String userName;
}
