package ru.javaSchoolProject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.TariffDto;
import ru.javaSchoolProject.models.Tariff;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("tariff")
public class TariffController {

    @PutMapping("add_new") //for admin
    public TariffDto addNewTariff(){
        return new TariffDto();
    }

    @GetMapping("get_all_tariffs")
    public TariffDto getAllTariffs(){
        return new TariffDto();
    }

    @DeleteMapping("delete_tariff/{id}") //for admin
    public TariffDto deleteTariff(@PathVariable("id") String id, @RequestBody TariffDto tariffDto){
        return new TariffDto();
    }

}
