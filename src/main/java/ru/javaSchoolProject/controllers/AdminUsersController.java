package ru.javaSchoolProject.controllers;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.models.User;
import ru.javaSchoolProject.security.JwtProvider;
import ru.javaSchoolProject.services.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("manage/")
public class AdminUsersController {
    final static Logger logger = Logger.getLogger(AuthController.class.getName());

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        logger.info("GET request: all users are taken from database");
        return userService.findAllUsers();
    }


}