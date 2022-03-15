package ru.javaSchoolProject.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javaSchoolProject.dao.UserDao;
import ru.javaSchoolProject.enums.Role;
import ru.javaSchoolProject.models.User;

public class UserServiceTest {


    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("register same user multiple times")
    public void regSameUserTest(){

        when(userDao.findByLogin("login")).thenReturn(new User("login","password", Role.CUSTOMER)); //user exists
        userService.saveUser("login","password");
        assertEquals("User with this login already exists",userService.saveUser("login","password").getToken());
    }



    @Test
    @DisplayName("log user not found")
    public void logUnknownUserTest(){

        when(userDao.findByLogin("login")).thenReturn(null);

        assertEquals(null,userService.logUser("login","password").getId());
    }
}
