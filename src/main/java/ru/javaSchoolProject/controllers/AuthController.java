package ru.javaSchoolProject.controllers;

import ru.javaSchoolProject.dto.LogInDto;
import ru.javaSchoolProject.dto.RegDto;
import ru.javaSchoolProject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("auth/")
public class AuthController {

    @Autowired
    private UserService userService;



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String printHelloWorld(ModelMap modelMap){
        // return the name of the file to be loaded "hello_world.jsp"
        return "hello_world";
    }

    @PostMapping("/logIn")
    public LogInDto postLogUser(@RequestBody UserDto user){
        System.out.println(user.getLogin());
        return userService.logUser(user.getLogin(),user.getPassword());
    }

    @PostMapping("/reg")
    public RegDto postRegUser(@RequestBody UserDto user){
        System.out.println(user.getLogin());
        return userService.saveUser(user.getLogin(),user.getPassword());
    }



}