package ru.javaSchoolProject.controllers;


import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.ContractDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("contracts")
public class ContractController {

    @PostMapping(value = "/sign")
    public ContractDto signContract(@RequestBody ContractDto contract){
        //logic
        return new ContractDto();
    }

}
