package ru.javaSchoolProject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.*;
import ru.javaSchoolProject.services.ContractService;

import java.util.List;

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

    @PostMapping(value = "/get_contract")
    public FullContractDto getContract(@RequestBody ContractIdAndNumberDto contractIdAndNumberDto){
        return contractService.getContract(contractIdAndNumberDto);
    }

    @GetMapping("/contract_ids_and_phone_numbers_of_user_{userId}")
    public List<ContractIdAndNumberDto> getContractIdsAndNumbers(@PathVariable String userId){
        return  contractService.getContractIdsAndNumbers(userId);
    }

    @DeleteMapping("delete_contract_{id}")
    public ContractAnswerDto deleteContract(@PathVariable String id){
        return contractService.deleteContract(id);
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
