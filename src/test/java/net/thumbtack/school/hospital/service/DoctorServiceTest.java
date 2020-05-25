package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorServiceTest {

    @MockBean
    private UserDao userDao;
    @MockBean
    private DoctorDao doctorDao;
    @MockBean
    private ScheduleDao scheduleDao;
    @MockBean
    private AdminDao adminDao;

    @Autowired
    private DoctorService doctorService;


    @Test
    public void testDoctorInfo() throws ServerException {
        User user = new User();
        Doctor doctor = new Doctor(new User("firstName", "lastName", "patronymic", "login", "password", new Session("cookie")), new Speciality("spec"), new Room("room"));

        when(userDao.getByToken(anyString())).thenReturn(null);
        when(userDao.getByToken("cookie")).thenReturn(user);
        when(doctorDao.getByDoctorId(anyInt())).thenReturn(null);
        when(doctorDao.getByDoctorId(123)).thenReturn(doctor);

        EditScheduleDtoResponse response;
        try {
            doctorService.doctorInfo("BadCookie", 123, "no", "01-04-2020", "01-05-2020");
            fail();
        } catch (ServerException ex) {
            assertEquals(ServerError.TOKEN_INVALID, ex.getError());
        }

        try {
            doctorService.doctorInfo("cookie", 1234, "no", "01-04-2020", "01-05-2020");
            fail();
        } catch (ServerException ex) {
            assertEquals(ServerError.USER_ID_INVALID, ex.getError());
        }

        response = doctorService.doctorInfo("cookie", 123, "no", "01-04-2020", "01-05-2020");
        assertAll(
                () -> assertEquals("firstName", response.getFirstName()),
                () -> assertEquals("lastName", response.getLastName()),
                () -> assertEquals("patronymic", response.getPatronymic()),
                () -> assertEquals("spec", response.getSpeciality()),
                () -> assertEquals("room", response.getRoom())
        );
    }
}
