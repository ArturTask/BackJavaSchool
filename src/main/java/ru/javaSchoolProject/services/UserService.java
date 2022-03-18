package ru.javaSchoolProject.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.controllers.AuthController;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.*;
import ru.javaSchoolProject.enums.Role;
import ru.javaSchoolProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.javaSchoolProject.security.JwtProvider;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao usersDao;

    @Autowired
    private JwtProvider jwtProvider;

    final static Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService() {
    }

    public User findUserById(int id) {
        try {
            return usersDao.findById(id);
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public User findUserByLogin(String login) {
        // If not found returns null
        return usersDao.findByLogin(login);

    }

    public LogInDto logUser (String login, String password) throws SecurityException {

        //check if user with current login exists: returns null if not, otherwise returns User object
        User currentUser = findUserByLogin(login);

        if(currentUser==null){
            logger.warn("CAN'T LOGIN user \""+login +"\" login is not registered: FAILURE");
            return new LogInDto(); //invalid username
        }
        else {
            if(passwordEncoder.matches(password,currentUser.getPassword())){
                //logger.info("LOGIN user \""+login+"\": SUCCESS");
                String token = jwtProvider.generateToken(login); //generate token if password matches
                currentUser.setToken(token);
                updateUser(currentUser); // save token to our db!!! very important
                return new LogInDto(token,Integer.toString(currentUser.getId()),currentUser.getRole().toString());
            }
            //logger.info("CAN'T LOGIN user \""+login+"\" Incorrect password: FAILURE");
            return new LogInDto(); //invalid password
        }


    }

    public RegDto saveUser(String login, String password) {

            //check if user with current login exists: returns null if not otherwise returns User object
            User existingUser = usersDao.findByLogin(login);

            if(existingUser==null){

                usersDao.save(new User(login,passwordEncoder.encode(password),Role.CUSTOMER)); //saving the CUSTOMER!
                //logger.info("REGISTER user \""+login+"\": SUCCESS");
                return new RegDto("","ROLE_CUSTOMER");
            }
            else { //User with this login already exists
                //logger.warn("CAN'T REGISTER new user \""+login +"\" already exists, must be unique: FAILURE");
                return new RegDto("User with this login already exists","");
            }


    }

    public BlockUserDto changeBlockUserByAdmin(String userId){
        try{ //easy check of input data
            Integer.parseInt(userId);
        }catch (NumberFormatException e){
            return new BlockUserDto("Invalid user id");
        }
        User currentUser = usersDao.findById(Integer.parseInt(userId));
        if(currentUser!=null) {
            if (currentUser.getRole() != Role.ADMIN) {
                currentUser.setBlocked(!currentUser.isBlocked());
                // block/unblock by admin - if blocked = true else = false
                currentUser.setBlockedByAdmin(currentUser.isBlocked());

                updateUser(currentUser);
                return new BlockUserDto("User "+currentUser.getLogin()+" is now blocked: "+currentUser.isBlocked());
            }
            return new BlockUserDto("Cant block/unblock admin");
        }
        return new BlockUserDto("Cant find user with that id ("+userId+") in db");

    }

    public BlockUserDto changeBlockUser(String userId){
        try{ //easy check of input data
            Integer.parseInt(userId);
        }catch (NumberFormatException e){
            return new BlockUserDto("Invalid user id");
        }
        User currentUser = usersDao.findById(Integer.parseInt(userId));
        if(currentUser!=null) {
            if (currentUser.getRole() != Role.ADMIN) {
                if(!currentUser.isBlockedByAdmin()) {
                    currentUser.setBlocked(!currentUser.isBlocked());
                    updateUser(currentUser);
                    return new BlockUserDto("User " + currentUser.getLogin() + " is now blocked: " + currentUser.isBlocked());
                }
                return new BlockUserDto("Cant unblock: user " + userId + " is blocked by admin");
            }
            return new BlockUserDto("Cant block/unblock admin");
        }
        return new BlockUserDto("Cant find user with that id ("+userId+") in db");

    }

    public IsUserBlockedDto isUserBlocked(String userId){
        try{ //easy check of input data
            Integer.parseInt(userId);
        }catch (NumberFormatException e){
            return new IsUserBlockedDto("Invalid user id",false);
        }
        User currentUser = usersDao.findById(Integer.parseInt(userId));
        if(currentUser!=null) {
            return new IsUserBlockedDto(null,currentUser.isBlocked());
        }
        return new IsUserBlockedDto("Cant find user with that id ("+userId+") in db",false);
    }

    public UserDto findUserByPhoneNumber(String phoneNumber){
        if(checkPhoneNumber(phoneNumber)){
            User currentUser = usersDao.findByPhoneNumber(Long.parseLong(phoneNumber));
            if(currentUser!=null){ // user is found
                //response entity
                UserDto userDto = new UserDto();
                userDto.setId(currentUser.getId());
                userDto.setLogin(currentUser.getLogin());
                return userDto;
            }
        }
        else{ //invalid number
            return new UserDto();
        } //user not found
        return new UserDto();
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<UserDto> findAllUsers() {
        List<User> users =  usersDao.findAll();
        List<UserDto> dtoUsers =  new ArrayList<>();
        for (User u : users){
            if(u.getRole()==Role.CUSTOMER) {
                dtoUsers.add(UserDto.builder()
                        .id(u.getId())
                        .login(u.getLogin())
                        .build());
            }
        }
        return dtoUsers;
    }

    //validators
    private static boolean checkPhoneNumber(String phoneNumber){
        try{
            Long.parseLong(phoneNumber);
        }
        catch (NumberFormatException e){
            return false;
        }
        if(phoneNumber.length()!=11){
            return false;
        }
        return phoneNumber.startsWith("8777"); //check if operator and country number is valid
    }


}