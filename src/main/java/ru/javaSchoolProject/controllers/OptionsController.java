package ru.javaSchoolProject.controllers;

import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.OptionsDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("options")
public class OptionsController {

    @PostMapping("change_options_of_{id}") //for admin
    public OptionsDto changeTariffOptions(@PathVariable String id, OptionsDto optionsDto){

        return new OptionsDto();
    }

    @DeleteMapping("delete_option")
    public OptionsDto deleteOption(@RequestBody OptionsDto optionsDto){
        return new OptionsDto();
    }


}
