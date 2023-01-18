package ru.javaSchoolProject.utils;

import ru.javaSchoolProject.dto.ContractDto;
import ru.javaSchoolProject.dto.FullContractDto;
import ru.javaSchoolProject.dto.OptionsDto;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.models.Contract;
import ru.javaSchoolProject.models.ContractOptions;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.models.Tariff;

import java.util.ArrayList;
import java.util.List;

// class is used to make objects from dto's
public class DtoUtils {

    public static List<ContractOptions> contractOptionsFromDto(ContractDto contractDto, Contract newContract) {
        List<ContractOptions> newContractOptions = new ArrayList<>();
        if (contractDto.getContractOptions() != null) {
            for (OptionsDto opDto : contractDto.getContractOptions()) {
                ContractOptions newOption = new ContractOptions();
                newOption.setContract(newContract);
                newOption.setOptionId(Integer.parseInt(opDto.getId()));
                newOption.setName(opDto.getName());
                newOption.setOptionType(OptionType.valueOf(opDto.getOptionType()));
                newOption.setCost(Double.parseDouble(opDto.getCost()));

                newContractOptions.add(newOption);
            }
        }
        return newContractOptions;
    }


    public static TariffDto tariffDtoFromTariff(Tariff currentTariff, List<OptionsDto> foundTariffOptions) {
        TariffDto tariffDto = new TariffDto();
        tariffDto.setId(currentTariff.getId());
        tariffDto.setTitle(currentTariff.getTitle());
        tariffDto.setDescription(currentTariff.getDescription());
        tariffDto.setOptions(foundTariffOptions);
        tariffDto.setCost(String.valueOf(currentTariff.getCost()));
        tariffDto.setActive(currentTariff.isActive());
        return tariffDto;
    }

    public static List<OptionsDto> optionsDtoFromOptions(Contract currentContract, Tariff currentTariff) {
        List<OptionsDto> foundTariffOptions = new ArrayList<>();
        if(currentTariff.getOptions()!=null){
            for (Options op:currentContract.getTariff().getOptions()) {
                OptionsDto foundOption = new OptionsDto();
                foundOption.setId(String.valueOf(op.getId()));
                foundOption.setName(op.getName());
                foundOption.setOptionType(String.valueOf(op.getOptionType()));
                foundOption.setCost(String.valueOf(op.getCost()));

                foundTariffOptions.add(foundOption);
            }
        }
        return foundTariffOptions;
    }

    public static List<OptionsDto> optionDtoFromContractOptions(Contract currentContract) {
        List<OptionsDto> foundContractOptions = new ArrayList<>();
        if(currentContract.getContractOptions()!=null){
            for (ContractOptions op: currentContract.getContractOptions()) {
                OptionsDto foundOption = new OptionsDto();
                foundOption.setId(String.valueOf(op.getId()));
                foundOption.setName(op.getName());
                foundOption.setOptionType(String.valueOf(op.getOptionType()));
                foundOption.setCost(String.valueOf(op.getCost()));

                foundContractOptions.add(foundOption);
            }
        }
        return foundContractOptions;
    }
    
    
    
}
