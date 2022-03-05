package ru.javaSchoolProject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.ContractAnswerDto;
import ru.javaSchoolProject.dto.ContractDto;
import ru.javaSchoolProject.dto.OptionsDto;
import ru.javaSchoolProject.services.ContractService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping(value = "/sign")
    public ContractAnswerDto signContract(@RequestBody ContractDto contract){
        //logic
        return  contractService.signContract(contract);
    }

//            System.out.println("got tariff id " +
//                    contract.getTariffId() +
//                    " with phone number " +
//                    contract.getPhoneNumber() +
//                    " from user with id " +
//                    contract.getUserId() +
//                    " lets see options" +
//                    "");
//        for (OptionsDto op:contract.getContractOptions()){
//        System.out.println("\nOption: id: " +
//                op.getId() +
//                " name: " +
//                op.getName() +
//                " cost: " +
//                op.getCost() +
//                " type: " +
//                op.getOptionType() +
//                " tariffId: " +
//                op.getTariffId());//always null
//    }

}
