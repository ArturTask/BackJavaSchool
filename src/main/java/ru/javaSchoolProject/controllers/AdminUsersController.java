package ru.javaSchoolProject.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dao.ContractDao;
import ru.javaSchoolProject.dto.BlockUserDto;
import ru.javaSchoolProject.dto.ContractInfoAboutUserDto;
import ru.javaSchoolProject.dto.UserDto;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.security.JwtProvider;
import ru.javaSchoolProject.services.ContractService;
import ru.javaSchoolProject.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("manage/")
public class AdminUsersController {

    final static Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(){
        logger.info("GET request: all users are taken from database");
        return userService.findAllUsers();
    }

    @GetMapping("/change_block_user_{userId}")
    public BlockUserDto changeBlockUserByAdmin(@PathVariable String userId){
        logger.info("GET request: block user with id " + userId);
        return userService.changeBlockUserByAdmin(userId);
    }

    @GetMapping("/getAllContractsUserInfo")
    public List<ContractInfoAboutUserDto> getAllContractsUserInfo(){
        return contractService.getAllContractsUserInfo();
    }




}
