package ru.javaSchoolProject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.javaSchoolProject.security.JwtProvider;
import ru.javaSchoolProject.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("proba/")
public class TableController {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private JwtProvider jwtProvider;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String printHelloWorld(ModelMap modelMap){
        return "hello_world";
    }


}
