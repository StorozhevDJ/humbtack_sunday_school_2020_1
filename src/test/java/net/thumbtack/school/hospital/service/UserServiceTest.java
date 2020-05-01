package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserDao userDao;
    @MockBean
    private DoctorDao doctorDao;

    @Autowired
    private UserService userService;

    @Test
    void testLoginUser() throws ServerException {
        User user = new User("Login", "password", new Session());
        Doctor doctor = new Doctor(user, new Speciality("spec"), new Room("room"));

        when(userDao.getByLogin(anyString(), anyString())).thenReturn(user);
        when(doctorDao.getByUserId(user.getId())).thenReturn(doctor);

        userService.loginUser(new LoginDtoRequest("Login", "password"), "asd");

        verify(userDao, times(1)).getByLogin(anyString(), anyString());
        verify(doctorDao, times(1)).getByUserId(anyInt());
    }
}
