package ru.javaSchoolProject.controllers;

import ru.javaSchoolProject.dto.LogInDto;
import ru.javaSchoolProject.dto.RegDto;
import ru.javaSchoolProject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.security.JwtProvider;
import ru.javaSchoolProject.services.UserService;

import org.apache.log4j.Logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    final static Logger logger = Logger.getLogger(AuthController.class.getName());



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String printHelloWorld(ModelMap modelMap){
        // return the name of the file to be loaded "hello_world.jsp"
//        logger.debug("Post req from ");
        return "hello_world";
    }

    @PostMapping("/logIn")
    public LogInDto postLogUser(@RequestBody UserDto user){
        logger.info("LOGIN POST request(/logIn) from user " + user.getLogin());
        String token = jwtProvider.generateToken(user.getLogin());
//        System.out.println(token);
        return userService.logUser(token, user.getLogin(),user.getPassword());


    }

    @PostMapping("/reg")
    public RegDto postRegUser(@RequestBody UserDto user){
        logger.info("REGISTRATION POST request(/reg) from user " + user.getLogin());
        return userService.saveUser(user.getLogin(),user.getPassword());
    }



}