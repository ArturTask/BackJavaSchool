package ru.javaSchoolProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.dao.TariffDao;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.models.Tariff;

import java.util.ArrayList;
import java.util.List;

@Service
public class TariffService {
    @Autowired
    private TariffDao tariffDao;

    //for admin
    public TariffDto addNewTariff(TariffDto tariffDto){
        return new TariffDto();
    }

    public List<TariffDto> getAllTariffs(){
        List<Tariff> tariffs = tariffDao.findAll();
        List<TariffDto> dtoTariffs = new ArrayList<>();

        for(Tariff tariff : tariffs){
            dtoTariffs.add(TariffDto.builder()
                    .id(tariff.getId())
                    .title(tariff.getTitle())
                    .description(tariff.getDescription())
                    .build());
        }
        return dtoTariffs;
    }

    //for admin
    public TariffDto deleteTariff(TariffDto tariffDto){
        return new TariffDto();
    }

}
