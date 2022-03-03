package ru.javaSchoolProject.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.controllers.AuthController;
import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dto.OptionsDto;
import ru.javaSchoolProject.dto.TariffAnswerDto;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.enums.OptionType;
import ru.javaSchoolProject.models.Options;
import ru.javaSchoolProject.models.Tariff;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffService {

    @Autowired
    private TariffDao tariffDao;

    @Autowired
    private OptionsDao optionsDao;

    final static Logger logger = Logger.getLogger(TariffService.class.getName());

    //for admin
    public TariffAnswerDto addNewTariff(TariffDto tariffDto){
        if(checkTariffDto(tariffDto)) { //valid
            List<OptionsDto> optionsDto = tariffDto.getOptions();
            List<Options> options = new ArrayList<>();
            Tariff currentTariff = new Tariff();
            for (OptionsDto opDto : optionsDto) {
                options.add(new Options(opDto.getName(), OptionType.valueOf(opDto.getOptionType()),Double.parseDouble(opDto.getCost()) ,currentTariff));
            }
            currentTariff.setTitle(tariffDto.getTitle());
            currentTariff.setDescription(tariffDto.getDescription());
            currentTariff.setCost(Double.parseDouble(tariffDto.getCost()));
            currentTariff.setOptions(options);
            currentTariff.setActive(true);
            if (tariffDao.addTariff(currentTariff)) { //saved to db
                return new TariffAnswerDto("OK");
            } else { // could not save
                return new TariffAnswerDto("Error");
            }
        }
        else { //invalid
            logger.warn("CANT ADD TARIFF: Invalid Tariff data");
            return new TariffAnswerDto("Invalid data from frontend");
        }
    }


    public TariffDto findTariffById(String id){
        if(checkTariffId(id)){ //valid id
            //find tariff by id returns null if not found
            Tariff currentTariff = tariffDao.findTariffById(Integer.parseInt(id));
            if(currentTariff!=null){
                TariffDto tariffDto = new TariffDto();
                tariffDto.setId(currentTariff.getId());
                tariffDto.setTitle(currentTariff.getTitle());
                tariffDto.setDescription(currentTariff.getDescription());
                tariffDto.setCost(String.valueOf(currentTariff.getCost()));
                tariffDto.setActive(currentTariff.isActive());
                //options
                List<Options> currentOptions = currentTariff.getOptions();
                List<OptionsDto> optionsDtos = new ArrayList<>();
                for(Options option: currentOptions){
                    optionsDtos.add(new OptionsDto(String.valueOf(option.getId()),option.getName(),option.getOptionType().toString(),String.valueOf(option.getCost()),String.valueOf(option.getTariff().getId())));
                }
                tariffDto.setOptions(optionsDtos);
                return tariffDto;
            }
            else {// if currentTariff == null
                logger.warn("NOT FOUND: Tariff not found");
                return new TariffDto();
            }
        }
        else { //invalid id
            logger.warn("NOT FOUND: Invalid tariff id");
            return new TariffDto();
        }
    }

    public TariffAnswerDto updateTariff(TariffDto tariffDto){
        if(checkTariffDto(tariffDto)){ //valid tariffDto
            if(checkTariffIdForUpdate(tariffDto.getId())){

                Tariff currentTariff = tariffDao.findTariffById(tariffDto.getId());
                currentTariff = updateOptions(currentTariff,tariffDto);

                currentTariff.setTitle(tariffDto.getTitle());
                currentTariff.setDescription(tariffDto.getDescription());
                currentTariff.setCost(Double.parseDouble(tariffDto.getCost()));
                if(tariffDao.updateTariff(currentTariff)){
                    logger.info("UPDATE TARIFF: success");
                    return new TariffAnswerDto("OK");
                }
                else {
                    logger.warn("CANT UPDATE TARIFF: Error");
                    return new TariffAnswerDto("Error");
                }
            }
            else {//incorrect tariff id
                logger.warn("CANT UPDATE TARIFF: Invalid id");
                return new TariffAnswerDto("Invalid id");
            }
        }
        else {//invalid tariffDto
            logger.warn("CANT UPDATE TARIFF: Invalid data");
            return new TariffAnswerDto("Invalid Tariff data");
        }
    }

    public List<TariffDto> getAllTariffs(){ //for admin
        List<Tariff> tariffs = tariffDao.findAll();
        List<TariffDto> dtoTariffs = new ArrayList<>();

        for(Tariff tariff : tariffs){
            dtoTariffs.add(TariffDto.builder()
                    .id(tariff.getId())
                    .title(tariff.getTitle())
                    .description(tariff.getDescription())
                    .isActive(tariff.isActive())
                    .build());
        }
        return dtoTariffs;
    }

    public List<TariffDto> getAllActiveTariffs(){ //for customer
        List<Tariff> tariffs = tariffDao.findAll();
        List<TariffDto> dtoTariffs = new ArrayList<>();

        for(Tariff tariff : tariffs){
            if(tariff.isActive()) {
                dtoTariffs.add(TariffDto.builder()
                        .id(tariff.getId())
                        .title(tariff.getTitle())
                        .description(tariff.getDescription())
                        .isActive(tariff.isActive())
                        .build());
            }
        }
        return dtoTariffs;
    }

    //for admin
    public TariffAnswerDto deleteTariffById(String id){
        if(checkTariffId(id)) {
            Tariff currentTariff = tariffDao.findTariffById(Integer.parseInt(id));
            if (currentTariff != null) {
                currentTariff.setActive(!currentTariff.isActive());
                tariffDao.updateTariff(currentTariff);
                logger.info("DELETE: Successfully Deleted Tariff id = " + id);
            } else {
                logger.warn("CANT DELETE: Tariff not found");
                return new TariffAnswerDto("Can't delete Tariff id = " + id + ", tariff with that id does not exist");
            }
            return new TariffAnswerDto("Successfuly deleted Tariff id = " + id);
        }
        else {
            logger.warn("CANT DELETE: Invalid Tariff id");
            return new TariffAnswerDto("Can't delete Tariff id = " + id + ", tariff with that id does not exist");
        }
    }

    //util not done
    private Tariff updateOptions(Tariff tariffEntity,TariffDto tariffDto){
        List<Options> options = tariffEntity.getOptions();
        List<OptionsDto> optionsDto = tariffDto.getOptions();

        int j =0;
        //delete some options
        for(int i=0;i<options.size();i++){ //we go through elems in dao
            int daoId = options.get(i).getId();
            int dtoId = Integer.parseInt(optionsDto.get(j).getId());
            if(dtoId<0){//it means that we have reached new options
                optionsDao.deleteOptionById(daoId);
            }
            else if(daoId==dtoId){//we didnt delete option
                j++;
            }
            else if(daoId<dtoId){//we deleted this option
                optionsDao.deleteOptionById(daoId);
            }
        }

        List<Options> newOptions = new ArrayList<>();

        for (OptionsDto opDto : optionsDto) {//parse options from frontend
            if(Integer.parseInt(opDto.getId())>0){
                newOptions.add(new Options(Integer.parseInt(opDto.getId()),opDto.getName(), OptionType.valueOf(opDto.getOptionType()), Double.parseDouble(opDto.getCost()) , tariffEntity));
            }
            else {
                newOptions.add(new Options(opDto.getName(), OptionType.valueOf(opDto.getOptionType()), Double.parseDouble(opDto.getCost()), tariffEntity));
            }
        }
        tariffEntity.setOptions(newOptions);
        return tariffEntity;
    }

    //validators
    private boolean checkTariffDto(TariffDto tariffDto){
        if(tariffDto.getDescription()==null || tariffDto.getDescription().equals("") || tariffDto.getTitle()==null || tariffDto.getTitle().equals("")){
            return false;
        }
        if(!checkOptions(tariffDto.getOptions())){
            return false;
        }
        if(!checkCost(tariffDto.getCost())){
            return false;
        }
        return true;
    }

    private boolean checkOptions(List<OptionsDto> optionsDtos){
        for (OptionsDto opDto:optionsDtos) {
            if(opDto.getName()==null || opDto.getName().equals("") || opDto.getOptionType()==null || opDto.getOptionType().equals("")){
                return false;
            }
            if(!checkCost(opDto.getCost())){
                return false;
            }
        }
        return  true;
    }

    private boolean checkTariffId(String id){
        if(id!=null && !id.equals("")){
            try{
                Integer.parseInt(id);
            }
            catch (NumberFormatException e){
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkCost(String cost){
        if(cost!=null&&!cost.equals("")){
            try{
                Double.parseDouble(cost);
            }
            catch (NumberFormatException e){
                return false;
            }
            if(Double.parseDouble(cost)<0){
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkTariffIdForUpdate(Integer id){
        if(!checkTariffId(id.toString())){
            return false;
        }
        return tariffDao.findTariffById(id) != null;
    }

}
