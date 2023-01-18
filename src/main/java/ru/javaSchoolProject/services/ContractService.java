package ru.javaSchoolProject.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.dao.ContractDao;
import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.*;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.models.*;

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

        User currentUser = userDao.findById(Integer.parseInt(contractDto.getUserId()));
        ContractAnswerDto contractAnswerDto = checkForFailure(contractDto, currentUser);

        if (contractAnswerDto!=null){
            return contractAnswerDto;
        }

        Contract newContract = makeContract(contractDto, currentUser );
        return saveContract(newContract);


    }

    private ContractAnswerDto checkForFailure(ContractDto contractDto, User currentUser){
        if(!checkContractDto(contractDto)) { //check phoneNum, userId, tariffId
            return new ContractAnswerDto("Invalid contract data(wrong phone number or user/tariff id)");
        }
        if(currentUser.isBlocked()){
            return new ContractAnswerDto("User is blocked");
        }
        Tariff tariff = tariffDao.findTariffById(Integer.parseInt(contractDto.getTariffId()));
        if (tariff == null) { //tariff not found
            return new ContractAnswerDto("Tariff not found");
        }
        if (checkContractDtoOptions(contractDto, tariff)) { //check if all options are from chosen tariff
            return new ContractAnswerDto("cant save contract options are invalid"); // didn't pass option check
        }
        return null;
    }

    private ContractAnswerDto saveContract(Contract newContract) {
        if (contractDao.save(newContract)) {
            return new ContractAnswerDto("Success");
        } else {//NOT unique phone number
            return new ContractAnswerDto("Telephone number: " + newContract.getPhoneNumber() + " is already taken");
        }
    }

    private Contract makeContract(ContractDto contractDto, User currentUser) {
        Contract newContract = new Contract();
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
        newContract.setPhoneNumber(Long.parseLong(contractDto.getPhoneNumber()));
        newContract.setTariff(tariffDao.findTariffById(Integer.parseInt(contractDto.getTariffId())));
        newContract.setUser(currentUser);
        newContract.setContractOptions(newContractOptions);
        return newContract;
    }



    public FullContractDto getContract(ContractIdAndNumberDto contractIdAndNumberDto){
        try{ //easy check
            Integer.parseInt(contractIdAndNumberDto.getContractId());
            Long.parseLong(contractIdAndNumberDto.getPhoneNumber());
        }
        catch (NumberFormatException e){//return null
            return new FullContractDto();
        }

        //dao contract
        Contract currentContract = contractDao.getContractById(Integer.parseInt(contractIdAndNumberDto.getContractId()));
        //contract not found
        if(currentContract==null){
            return new FullContractDto();
        }
        //check if number belongs to that contract
        if(currentContract.getPhoneNumber()!=Long.parseLong(contractIdAndNumberDto.getPhoneNumber())){ //wrong number
            return new FullContractDto();
        }

        //parse contractOptions to optionDtos
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

        //parse options of tariff in contract to optionsDto (all options of tariff)
        Tariff currentTariff = currentContract.getTariff();
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

        //parse tariff to tariffDto
        TariffDto tariffDto = new TariffDto();
        tariffDto.setId(currentTariff.getId());
        tariffDto.setTitle(currentTariff.getTitle());
        tariffDto.setDescription(currentTariff.getDescription());
        tariffDto.setOptions(foundTariffOptions);
        tariffDto.setCost(String.valueOf(currentTariff.getCost()));
        tariffDto.setActive(currentTariff.isActive());

        //make dto response contract
        FullContractDto foundContract = new FullContractDto();
        foundContract.setId(String.valueOf(currentContract.getId()));
        foundContract.setPhoneNumber(String.valueOf(currentContract.getPhoneNumber()));
        foundContract.setUserId(String.valueOf(currentContract.getUser().getId()));

        foundContract.setContractOptions(foundContractOptions);
        foundContract.setTariffDto(tariffDto);

        return foundContract;

    }

    public List<ContractIdAndNumberDto> getContractIdsAndNumbers(String userId){
        try{
            Integer.parseInt(userId);
        }catch (NumberFormatException e){// cant parse userId
            return new ArrayList<>();
        }
        //get contract info by user id
        List<Contract> currentContracts = contractDao.getContractsOfUser(Integer.parseInt(userId));
        //initialize dto response entity
        List<ContractIdAndNumberDto> foundContractIdAndNumberDtoList = new ArrayList<>();

        if(currentContracts!=null) {
            for (Contract c : currentContracts) {
                foundContractIdAndNumberDtoList.add(new ContractIdAndNumberDto(String.valueOf(c.getId()), String.valueOf(c.getPhoneNumber())));
            }
        }
        return foundContractIdAndNumberDtoList;
    }

    public ContractAnswerDto deleteContract(String id){
        try { //easy check
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new ContractAnswerDto("Invalid id");
        }
        Contract currentContract = contractDao.getContractById(Integer.parseInt(id));
        if(currentContract!=null) {
            if (!currentContract.getUser().isBlocked()) {
                if (!contractDao.deleteContract(Integer.parseInt(id))) {
                    return new ContractAnswerDto("Cant delete contract from database");
                }
                return new ContractAnswerDto("Delete success");
            }
            //user is blocked
            return new ContractAnswerDto("Cant delete tariff: user is blocked");
        }
        return  new ContractAnswerDto("Cant delete tariff: tariff not found");
    }

    public List<ContractInfoAboutUserDto> getAllContractsUserInfo(){
        //get all contract info about users
        List<Contract> currentContracts = contractDao.getAllContracts();
        //initialize dto response entity
        List<ContractInfoAboutUserDto> foundContractInfoAboutUserDtoList = new ArrayList<>();

        if(currentContracts!=null) {
            for (Contract c : currentContracts) {
                foundContractInfoAboutUserDtoList.add(new ContractInfoAboutUserDto(String.valueOf(c.getId()), String.valueOf(c.getPhoneNumber()),String.valueOf(c.getUser().getId()),c.getUser().getLogin()));
            }
        }
        return foundContractInfoAboutUserDtoList;
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
        if(contractDto.getPhoneNumber().length()!=11){
            return false;
        }
        return contractDto.getPhoneNumber().startsWith("8777"); //check if operator and country number is valid
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
