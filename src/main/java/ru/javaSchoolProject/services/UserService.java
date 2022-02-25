package ru.javaSchoolProject.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.javaSchoolProject.controllers.AuthController;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.LogInDto;
import ru.javaSchoolProject.dto.RegDto;
import ru.javaSchoolProject.dto.UserDto;
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
        logger.info("TRYING TO find user "+login+" by their's login");
        return usersDao.findByLogin(login);

    }

    public LogInDto logUser (String login, String password) throws SecurityException {

        //check if user with current login exists: returns null if not, otherwise returns User object
        User currentUser = findUserByLogin(login);

        if(currentUser==null){
            logger.warn("CAN'T LOGIN user \""+login +"\" login is not registered: FAILURE");
            return new LogInDto("Invalid username","","");
        }
        else {
            if(passwordEncoder.matches(password,currentUser.getPassword())){
                logger.info("LOGIN user \""+login+"\": SUCCESS");
                String token = jwtProvider.generateToken(login); //generate token if password matches
                currentUser.setToken(token);
                updateUser(currentUser); // save token to our db!!! very important
//                System.out.println(token);
                return new LogInDto(token,Integer.toString(currentUser.getId()),currentUser.getRole().toString());
            }
        }
        logger.info("CAN'T LOGIN user \""+login+"\" Incorrect password: FAILURE");
        return new LogInDto("Invalid Password","","");


    }

    public RegDto saveUser(String login, String password) {

            //check if user with current login exists: returns null if not otherwise returns User object
            User existingUser = usersDao.findByLogin(login);

            if(existingUser==null){

                usersDao.save(new User(login,passwordEncoder.encode(password),Role.CUSTOMER)); //saving the CUSTOMER!
                logger.info("REGISTER user \""+login+"\": SUCCESS");
                return new RegDto("","ROLE_CUSTOMER");
            }
            else { //User with this login already exists
                logger.warn("CAN'T REGISTER new user \""+login +"\" already exists, must be unique: FAILURE");
                return new RegDto("User with this login already exists","");
            }


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
            dtoUsers.add(UserDto.builder()
                    .id(u.getId())
                    .login(u.getLogin())
                    .build());
        }
        return dtoUsers;
    }

}