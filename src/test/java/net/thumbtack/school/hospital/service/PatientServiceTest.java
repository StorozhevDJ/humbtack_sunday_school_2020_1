package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PatientServiceTest {
    @MockBean
    private UserDao userDao;
    @MockBean
    private DoctorDao doctorDao;
    @MockBean
    private ScheduleDao scheduleDao;
    @MockBean
    private AdminDao adminDao;
    @MockBean
    private PatientDao patientDao;

    @Autowired
    private PatientService patientService;

    @Test
    public void testRegisterPatient() throws ServerException {
        Patient patient = new Patient(
                new User("firstName", "lastName", "patronymic", "login", "password", new Session("cookie")),
                "email",
                "addr",
                "+790012345678");

        when(patientDao.insert(any())).thenReturn(patient);

        LoginDtoResponse response = patientService.registerPatient("cookie", new RegisterPatientDtoRequest(
                "firstName",
                "lastName",
                "patronymic",
                "email",
                "addr",
                "+790012345678",
                "login",
                "password"));
        assertAll(
                () -> assertEquals("firstName", response.getFirstName()),
                () -> assertEquals("lastName", response.getLastName()),
                () -> assertEquals("patronymic", response.getPatronymic()),
                () -> assertEquals("email", response.getEmail()),
                () -> assertEquals("addr", response.getAddress()),
                () -> assertEquals("+790012345678", response.getPhone())
        );
    }

    @Test
    public void testInfoPatient() throws ServerException {
        User user = new User("firstName", "lastName", "patronymic", "login", "password", new Session("cookie"));
        user.setUserType(UserType.DOCTOR);
        Patient patient = new Patient(
                new User("firstName", "lastName", "patronymic", "login", "password", new Session("cookie")),
                "email",
                "addr",
                "+790012345678");

        when(userDao.getByToken("cookie")).thenReturn(user);
        when(patientDao.getByPatientId(123)).thenReturn(patient);

        try {
            patientService.infoPatient("BadCookie", 123);
            fail();
        } catch (ServerException ex) {
        }
        try {
            patientService.infoPatient("cookie", 231);
            fail();
        } catch (ServerException ex) {
        }

        LoginDtoResponse response = patientService.infoPatient("cookie", 123);

        assertAll(
                () -> assertEquals("firstName", response.getFirstName()),
                () -> assertEquals("lastName", response.getLastName()),
                () -> assertEquals("patronymic", response.getPatronymic()),
                () -> assertEquals("email", response.getEmail()),
                () -> assertEquals("addr", response.getAddress()),
                () -> assertEquals("+790012345678", response.getPhone())
        );
    }
}
