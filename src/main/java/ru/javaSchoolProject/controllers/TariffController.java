package ru.javaSchoolProject.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.OptionsDto;
import ru.javaSchoolProject.dto.TariffAnswerDto;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.services.TariffService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("tariff")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    final static Logger logger = Logger.getLogger(TariffController.class.getName());

    @PostMapping("add_new") //for admin
    public TariffAnswerDto addNewTariff(@RequestBody TariffDto tariffDto){
        logger.info("ADD TARIFF POST request");
        return tariffService.addNewTariff(tariffDto);
    }

    @GetMapping("find_tariff_{id}")
    public TariffDto findTariffById (@PathVariable String id){
        logger.info("GET FIND TARIFF by id = "+id);
        return tariffService.findTariffById(id);
    }

    @PostMapping("update_tariff") //for admin
    public TariffAnswerDto updateTariff (@RequestBody TariffDto tariffDto){
        logger.info("POST UPDATE TARIFF by id = "+tariffDto.getId());
        return tariffService.updateTariff(tariffDto);
    }

    @GetMapping("get_all_tariffs") //for admin
    public List<TariffDto> getAllTariffs(){
        logger.info("GET ALL TARIFFS request");
        return tariffService.getAllTariffs();
    }

    @GetMapping("get_all_active_tariffs") //for customer
    public List<TariffDto> getAllActiveTariffs(){
        return tariffService.getAllActiveTariffs();
    }

    @DeleteMapping("delete_{id}") //for admin
    public TariffAnswerDto deleteTariff(@PathVariable String id){
        logger.info("DELETE TARIFF request id = "+id);
        return tariffService.deleteTariffById(id);
    }

}
