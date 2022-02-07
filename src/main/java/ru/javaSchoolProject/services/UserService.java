package ru.javaSchoolProject.services;

import org.springframework.stereotype.Service;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.dto.LogInDto;
import ru.javaSchoolProject.dto.RegDto;
import ru.javaSchoolProject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserDao usersDao;
//    private UserDao usersDao = new UserDao();

    public UserService() {
    }

    public User findUserById(int id) {
        try {
            return usersDao.findById(id);
        }
        catch (NullPointerException e){
            System.out.println("Can't find user with that id");
            return null;
        }
    }

    public User findUserByLogin(String login) {
        return usersDao.findByLogin(login);
    }

    public LogInDto logUser(String login, String password) throws SecurityException {
        try{
            User currentUser = usersDao.findByLoginAndPassword(login, password);
            return new LogInDto("token",Integer.toString(currentUser.getId()),currentUser.getRole().toString());
        }
        catch (NullPointerException e){
            return new LogInDto("Invalid username or password","","");
//            throw new SecurityException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid username or password");
//            return "Wrong login or password";
        }

    }

    public RegDto saveUser(String login, String password) {

            //check if user with current login exists: returns null if not otherwise returns User object
            User existingUser = usersDao.findByLogin(login);

            if(existingUser==null){
                usersDao.save(new User(login,password)); //saving the CUSTOMER!
                return new RegDto("");
            }
            else { //User with this login already exists
                return new RegDto("User with this login already exists","");
            }


    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

}