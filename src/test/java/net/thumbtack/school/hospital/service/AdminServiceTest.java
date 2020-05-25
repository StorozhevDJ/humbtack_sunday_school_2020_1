package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.EditAdminDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.request.WeekScheduleDtoRequest;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AdminServiceTest {
    @MockBean
    private UserDao userDao;
    @MockBean
    private DoctorDao doctorDao;
    @MockBean
    private ScheduleDao scheduleDao;
    @MockBean
    private AdminDao adminDao;

    @Autowired
    private AdminService adminService;

    @Test
    public void testEditAdmin() throws ServerException {
        Admin admin = new Admin(new User("Login", "password", new Session()), "position");

        when(adminDao.getByToken("cookie")).thenReturn(admin);

        LoginDtoResponse response = adminService.editAdmin(new EditAdminDtoRequest("firstName", "lastName", "patronymic", "position",
                "oldPassword", "newPassword"), "cookie");

        verify(userDao, times(1)).update(any());
        assertAll(
                () -> assertNull(response.getEmail()),
                () -> assertNull(response.getAddress()),
                () -> assertNull(response.getPhone()),
                () -> assertNull(response.getSpeciality()),
                () -> assertNull(response.getRoom()),
                () -> assertEquals("firstName", response.getFirstName()),
                () -> assertEquals("lastName", response.getLastName()),
                () -> assertEquals("patronymic", response.getPatronymic()),
                () -> assertEquals("position", response.getPosition())
        );
    }


    @Test
    public void testRegisterDoctor() throws ServerException {
        User user = new User();
        user.setUserType(UserType.ADMINISTRATOR);
        Doctor doctor = new Doctor(new User("firstName", "lastName", "patronymic", "login", "password", new Session("cookie")), new Speciality("spec"), new Room("room"));

        when(userDao.getByToken("cookie")).thenReturn(user);
        when(doctorDao.insert(any())).thenReturn(doctor);
        when(scheduleDao.createSchedule(anyList())).thenReturn(1);

        EditScheduleDtoResponse response = adminService.registerDoctor(new RegisterDoctorDtoRequest(
                "firstName",
                "lastName",
                "patronymic",
                "speciality",
                "room",
                "login",
                "password",
                "01-04-2020",
                "01-05-2020",
                new WeekScheduleDtoRequest("08:00", "14:00", new String[]{"Mon"}),
                15
        ), "cookie");
        assertAll(
                () -> assertEquals("firstName", response.getFirstName()),
                () -> assertEquals("lastName", response.getLastName()),
                () -> assertEquals("patronymic", response.getPatronymic()),
                () -> assertEquals("spec", response.getSpeciality()),
                () -> assertEquals("room", response.getRoom()),
                () -> assertEquals(4, response.getSchedule().size()),
                () -> assertEquals("06-04-2020", response.getSchedule().get(0).getDate()),
                () -> assertEquals("13-04-2020", response.getSchedule().get(1).getDate()),
                () -> assertEquals(24, response.getSchedule().get(0).getTicketScheduleDto().size()),
                () -> assertEquals("08:00", response.getSchedule().get(0).getTicketScheduleDto().get(0).getTime()),
                () -> assertEquals("08:15", response.getSchedule().get(0).getTicketScheduleDto().get(1).getTime()),
                () -> assertNull(response.getSchedule().get(0).getTicketScheduleDto().get(0).getPatient())
        );
    }
}
