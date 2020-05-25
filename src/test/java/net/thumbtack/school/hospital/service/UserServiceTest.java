package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.LoginDtoRequest;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
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
    @MockBean
    private PatientDao patientDao;
    @MockBean
    private AdminDao adminDao;

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

    @Test
    void testInfoUser() throws ServerException {
        User userDoctor = new User("Login", "password", new Session());
        userDoctor.setId(1);
        User userPatient = new User("Login", "password", new Session());
        userPatient.setId(2);
        User userAdmin = new User("Login", "password", new Session());
        userAdmin.setId(3);
        Doctor doctor = new Doctor(userDoctor, new Speciality("spec"), new Room("room"));
        Patient patient = new Patient(userPatient, "email", "address", "phone");
        Admin admin = new Admin(userAdmin, "position");

        when(userDao.getByToken("CookieDoctor")).thenReturn(userDoctor);
        when(userDao.getByToken("CookiePatient")).thenReturn(userPatient);
        when(userDao.getByToken("CookieAdmin")).thenReturn(userAdmin);

        when(doctorDao.getByUserId(1)).thenReturn(doctor);
        when(patientDao.getByUserId(2)).thenReturn(patient);
        when(adminDao.getByUserId(3)).thenReturn(admin);

        LoginDtoResponse respDoctor = userService.infoUser("CookieDoctor");
        LoginDtoResponse respPatient = userService.infoUser("CookiePatient");
        LoginDtoResponse respAdmin = userService.infoUser("CookieAdmin");

        assertAll(
                () -> assertNull(respDoctor.getPosition()),
                () -> assertNull(respDoctor.getEmail()),
                () -> assertNull(respDoctor.getAddress()),
                () -> assertNull(respDoctor.getPhone()),
                () -> assertEquals("spec", respDoctor.getSpeciality()),
                () -> assertEquals("room", respDoctor.getRoom()),

                () -> assertNull(respPatient.getPosition()),
                () -> assertNull(respPatient.getSpeciality()),
                () -> assertNull(respPatient.getRoom()),
                () -> assertEquals("email", respPatient.getEmail()),
                () -> assertEquals("address", respPatient.getAddress()),
                () -> assertEquals("phone", respPatient.getPhone()),

                () -> assertNull(respAdmin.getEmail()),
                () -> assertNull(respAdmin.getAddress()),
                () -> assertNull(respAdmin.getPhone()),
                () -> assertNull(respAdmin.getSpeciality()),
                () -> assertNull(respAdmin.getRoom()),
                () -> assertEquals("position", respAdmin.getPosition())
        );
    }
}
