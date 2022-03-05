package ru.javaSchoolProject.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.dao.ContractDao;
import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.ContractAnswerDto;
import ru.javaSchoolProject.dto.ContractDto;
import ru.javaSchoolProject.dto.OptionsDto;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.models.Contract;
import ru.javaSchoolProject.models.ContractOptions;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.models.Tariff;

import javax.swing.plaf.DimensionUIResource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContractService {

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private UserDao userDao;

    final static Logger logger = Logger.getLogger(TariffService.class.getName());

    public ContractAnswerDto signContract(ContractDto contractDto){
        if(checkContractDto(contractDto)) { //check phoneNum, userId, tariffId
            Tariff tariff = tariffDao.findTariffById(Integer.parseInt(contractDto.getTariffId()));
            if(tariff!=null) { //tariff found
                if(checkContractDtoOptions(contractDto,tariff)){ //check if all options are from chosen tariff

                    Contract newContract = new Contract();
                    List<ContractOptions> newContractOptions = new ArrayList<>();

                    //make contractOptions from optionDto
                    if(contractDto.getContractOptions()!=null) {
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

                    //make contract
                    newContract.setPhoneNumber(Long.parseLong(contractDto.getPhoneNumber()));
                    newContract.setTariff(tariffDao.findTariffById(Integer.parseInt(contractDto.getTariffId())));
                    newContract.setUser(userDao.findById(Integer.parseInt(contractDto.getUserId())));
                    newContract.setContractOptions(newContractOptions);

                    if(contractDao.save(newContract)) {
                        return new ContractAnswerDto("Success");
                    }
                    else {//NOT unique phone number
                        return new ContractAnswerDto("Telephone number: "+newContract.getPhoneNumber()+" is already taken");
                    }
                }else { //didnt pass option check
                    return new ContractAnswerDto("cant save contract options are invalid");
                }
            }
            else { //tariff not found
                return new ContractAnswerDto("Tariff not found");
            }
        }
        else { //invalid contractDto data
            return new ContractAnswerDto("Invalid contract data(wrong phone number or user/tariff id)");
        }
    }


    //validators
    private static boolean checkContractDto(ContractDto contractDto){
        try{
            Integer.parseInt(contractDto.getTariffId());
            Long.parseLong(contractDto.getPhoneNumber());
            Integer.parseInt(contractDto.getUserId());
        }
        catch (NumberFormatException e){
            return false;
        }
        return contractDto.getPhoneNumber().startsWith("8777"); //check if number is valid
    }

    private static boolean checkContractDtoOptions(ContractDto contractDto, Tariff currentTariff){
        List<OptionsDto> chosenOptions = contractDto.getContractOptions();
        if(chosenOptions!=null){ //if options are chosen
            List<Options> currentOptions = currentTariff.getOptions();

            if(chosenOptions.size()>currentOptions.size()){ // you cant choose more options than possible
                logger.warn("CANT SIGN CONTRACT: options size is more than max");
                return false;
            }

            Set<Integer> optionIds = new HashSet<Integer>();
            for (Options op:currentOptions) { //create set of allowed options for current tariff
                optionIds.add(op.getId());
            }

            for (OptionsDto opDto:chosenOptions){
                try{
                    Integer.parseInt(opDto.getId());
                    Double.parseDouble(opDto.getCost());
                }
                catch (NumberFormatException e){
                    logger.warn("CANT SIGN CONTRACT: wrong type of parameter in option");
                    return false;
                }
                if(!optionIds.contains(Integer.parseInt(opDto.getId()))){
                    logger.warn("CANT SIGN CONTRACT: no option with id = "+opDto.getId());
                    return false;
                }


            }
            return true;
        } // no chosen options
        else {
            return true;
        }
    }

}
