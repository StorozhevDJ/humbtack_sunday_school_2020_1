package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class, ScheduleDaoImpl.class})
public class DayScheduleDaoTest extends DatabasePrepare {

    private final UserDao userDao;
    private final ScheduleDao scheduleDao;

    @Autowired
    public DayScheduleDaoTest(CommonDao commonDao,
                              AdminDao adminDao,
                              DoctorDao doctorDao,
                              PatientDao patientDao,
                              UserDao userDao,
                              ScheduleDao scheduleDao) {
        super(commonDao, adminDao, doctorDao, patientDao);
        this.userDao = userDao;
        this.scheduleDao = scheduleDao;
    }

    @Test
    public void testCreateSchedule() throws ServerException {

        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(dayScheduleList);
        assertEquals(8, insertedCnt);

        List<DaySchedule> dayScheduleList2 = scheduleDao.getByDoctorId(doc.getId(), null, null);
        List<DaySchedule> ts = scheduleDao.getByDoctorId(doc.getId(), null, null);

        assertAll(
                () -> assertNotEquals(0, ts.get(0).getId()),
                () -> assertEquals(1, ts.size()),
                () -> assertEquals(8, ts.get(0).getTicketSchedule().size()),
                () -> assertEquals(LocalDate.now(), ts.get(0).getDate()),
                () -> assertNotEquals(0, ts.get(0).getTicketSchedule().get(0).getId()),
                () -> assertNull(ts.get(0).getTicketSchedule().get(0).getPatient()),
                () -> assertNull(ts.get(0).getTicketSchedule().get(0).getTicket()),
                () -> assertEquals(LocalTime.parse("08:00", DateTimeFormatter.ofPattern("HH:mm")), ts.get(0).getTicketSchedule().get(0).getTimeStart()),
                () -> assertEquals(LocalTime.parse("08:15", DateTimeFormatter.ofPattern("HH:mm")), ts.get(0).getTicketSchedule().get(0).getTimeEnd()),
                () -> assertEquals(LocalTime.parse("08:15", DateTimeFormatter.ofPattern("HH:mm")), ts.get(0).getTicketSchedule().get(1).getTimeStart()),
                () -> assertEquals(LocalTime.parse("08:30", DateTimeFormatter.ofPattern("HH:mm")), ts.get(0).getTicketSchedule().get(1).getTimeEnd())
        );
    }

    @Test
    public void testGetScheduleBySpeciality() throws ServerException {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 10);
        try {
            dayScheduleList.get(0).getTicketSchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        } catch (ServerException e) {
            fail();
        }
        scheduleDao.createSchedule(dayScheduleList);
        List<DaySchedule> dayScheduleList2 = scheduleDao.getByDoctorSpeciality("spec", null, null);
        assertAll(
                () -> assertEquals(1, dayScheduleList2.size()),
                () -> assertNotEquals(0, dayScheduleList2.get(0).getId()),
                () -> assertEquals(LocalDate.now(), dayScheduleList2.get(0).getDate()),
                () -> assertNotNull(dayScheduleList2.get(0).getDoctor()),
                () -> assertEquals(10, dayScheduleList2.get(0).getTicketSchedule().size()),
                () -> assertNotEquals(0, dayScheduleList2.get(0).getTicketSchedule().get(0).getId()),
                () -> assertNull(dayScheduleList2.get(0).getTicketSchedule().get(0).getPatient()),
                () -> assertNull(dayScheduleList2.get(0).getTicketSchedule().get(0).getScheduleType()),
                () -> assertNotNull(dayScheduleList2.get(0).getTicketSchedule().get(0).getTimeStart())
        );
    }

    @Test
    public void testAddTicket() throws ServerException {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 8);
        scheduleDao.createSchedule(dayScheduleList);

        DaySchedule daySchedule = dayScheduleList.get(0);
        try {
            daySchedule.getTicketSchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        } catch (ServerException e) {
            fail();
        }
        daySchedule.getTicketSchedule().get(0).setTicket("testTicket");
        daySchedule.getTicketSchedule().get(0).setScheduleId(daySchedule.getId());

        assertTrue(scheduleDao.addTicket(daySchedule.getTicketSchedule().get(0)));

        List<TicketSchedule> savedScheduleList = scheduleDao.getTicketSchedule(daySchedule.getId(), ScheduleType.RECEPTION, null, null);
        assertAll(
                () -> assertEquals(1, savedScheduleList.size()),
                () -> assertNotEquals(0, savedScheduleList.get(0).getId()),
                () -> assertEquals(daySchedule.getTicketSchedule().get(0).getTimeStart(), savedScheduleList.get(0).getTimeStart()),
                () -> assertEquals(daySchedule.getTicketSchedule().get(0).getTimeEnd(), savedScheduleList.get(0).getTimeEnd()),
                () -> assertEquals(ScheduleType.RECEPTION, savedScheduleList.get(0).getScheduleType()),
                () -> assertEquals(daySchedule.getTicketSchedule().get(0).getTicket(), savedScheduleList.get(0).getTicket()),
                () -> assertEquals(daySchedule.getTicketSchedule().get(0).getPatient().getUser().getId(), savedScheduleList.get(0).getPatient().getUser().getId())
        );

    }


    private List<DaySchedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<DaySchedule> dayScheduleList = new ArrayList<>();

        List<TicketSchedule> ticketSchedule = new ArrayList<>();

        LocalTime lt = LocalTime.parse("08:00", DateTimeFormatter.ofPattern("HH:mm"));

        for (int i = 0; i < count; i++) {
            ticketSchedule.add(
                    new TicketSchedule(
                            "ticket",
                            lt.plusMinutes(i * interval),
                            lt.plusMinutes((i + 1) * interval),
                            new Patient(),
                            ScheduleType.RECEPTION
                    ));
        }
        dayScheduleList.add(new DaySchedule(
                doc,
                LocalDate.now(),
                ticketSchedule
        ));
        return dayScheduleList;
    }
}
