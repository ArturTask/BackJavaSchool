package ru.javaSchoolProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.dao.OptionsDao;
import ru.javaSchoolProject.dto.OptionsDto;

@Service
public class OptionsService {

    @Autowired
    private OptionsDao optionsDao;

    public OptionsDto deleteOption(OptionsDto optionsDto){
        optionsDao.deleteOptionById(Integer.parseInt(optionsDto.getId()));
        return new OptionsDto();
    }

}
