package ru.javaSchoolProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.models.Tariff;
import ru.javaSchoolProject.services.TariffService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("tariff")
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @PutMapping("add_new") //for admin
    public TariffDto addNewTariff(@RequestBody TariffDto tariffDto){
        return new TariffDto();
    }

    @GetMapping("get_all_tariffs")
    public List<TariffDto> getAllTariffs(){
        return tariffService.getAllTariffs();
    }

    @DeleteMapping("delete_tariff") //for admin
    public TariffDto deleteTariff(@RequestBody TariffDto tariffDto){
        return new TariffDto();
    }

}
