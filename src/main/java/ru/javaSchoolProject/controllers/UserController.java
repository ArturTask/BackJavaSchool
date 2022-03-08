package ru.javaSchoolProject.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.javaSchoolProject.dto.BlockUserDto;
import ru.javaSchoolProject.dto.IsUserBlockedDto;
import ru.javaSchoolProject.services.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("users/")
public class UserController {

    final static Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UserService userService;

    @GetMapping("/change_block_user_{userId}")
    public BlockUserDto changeBlockUser(@PathVariable String userId){
        return userService.changeBlockUser(userId);
    }

    @GetMapping("/is_blocked_user_{userId}")
    public IsUserBlockedDto isUserBlocked(@PathVariable String userId){
        return userService.isUserBlocked(userId);
    }
}
