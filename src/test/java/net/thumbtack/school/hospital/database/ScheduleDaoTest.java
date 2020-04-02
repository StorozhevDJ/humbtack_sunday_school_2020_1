package net.thumbtack.school.hospital.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import net.thumbtack.school.hospital.database.model.*;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDaoTest extends DatabaseTest {

    @Test
    public void testCreateSchedule() {

        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(scheduleList);
        assertEquals(8, insertedCnt);

        List<Schedule> scheduleList2 = scheduleDao.getByDoctorId(doc.getId());

        assertAll(
                () -> assertNotEquals(0, scheduleList2.get(1).getId()),
                () -> assertNull(scheduleList.get(0).getPatient()),
                () -> assertNull(scheduleList.get(0).getTicket()),
                () -> assertEquals(8, scheduleList2.size())
        );
    }

    @Test
    public void testGetAllSchedule() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<Schedule> scheduleList = createTestSchedule(doc, 20, 15);

        scheduleList.get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));

        scheduleDao.createSchedule(scheduleList);

        List<Schedule> scheduleList2 = scheduleDao.getAllShedule();

        assertEquals(15, scheduleList2.size());
    }

    @Test
    public void testGetScheduleBySpeciality() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<Schedule> scheduleList = createTestSchedule(doc, 15, 10);

        scheduleList.get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));

        scheduleDao.createSchedule(scheduleList);

        List<Schedule> scheduleList2 = scheduleDao.getByDoctorSpeciality("spec");

        assertEquals(10, scheduleList2.size());
    }

    @Test
    public void testAddTicket() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 15, 8);
        scheduleDao.createSchedule(scheduleList);

        Schedule schedule = scheduleList.get(5);
        schedule.setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.setTicket("testTicket");

        assertTrue(scheduleDao.addTicket(schedule));
        assertFalse(scheduleDao.addTicket(schedule));
    }

    @Test
    public void testAddCommission() {
        Doctor doc1 = doctorDao.getBySpeciality("spec").get(0);

        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality", "room")));
        Doctor doc2 = new Doctor(user, "spec", "6");
        doctorDao.insert(doc2);

        List<Schedule> scheduleList = createTestSchedule(doc1, 15, 8);
        scheduleDao.createSchedule(scheduleList);

        Schedule schedule = scheduleList.get(5);
        schedule.setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.setTicket("testTicket");

        assertTrue(scheduleDao.addTicket(schedule));

        List<Commission> commissionList = new ArrayList<>();
        commissionList.add(new Commission(schedule.getId(), doc2.getId()));

        assertEquals(0, commissionList.get(0).getId());
        assertEquals(1, scheduleDao.addCommission(commissionList));
        assertNotEquals(0, commissionList.get(0).getId());
        assertThrows(RuntimeException.class, () -> scheduleDao.addCommission(commissionList));
    }

    @Test
    public void testGetCommission() {
        Doctor doc1 = doctorDao.getBySpeciality("spec").get(0);

        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality", "room")));
        Doctor doc2 = new Doctor(user, "spec", "6");
        doctorDao.insert(doc2);

        List<Schedule> scheduleList = createTestSchedule(doc1, 15, 8);
        scheduleDao.createSchedule(scheduleList);

        Schedule schedule = scheduleList.get(5);
        schedule.setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.setTicket("testTicket");

        assertTrue(scheduleDao.addTicket(schedule));

        List<Commission> commissionList = new ArrayList<>();
        commissionList.add(new Commission(schedule.getId(), doc2.getId()));

        assertEquals(1, scheduleDao.addCommission(commissionList));

        scheduleList = scheduleDao.getByDoctorId(doc1.getId());
        assertNotNull(scheduleList.get(0).getCommission());
    }


    private List<Schedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            scheduleList.add(new Schedule(
                    doc,
                    new java.sql.Date(System.currentTimeMillis()),
                    Time.valueOf(LocalTime.now().plusMinutes(i * interval)),
                    Time.valueOf(LocalTime.now().plusMinutes((i + 1) * interval - 1))
            ));
        }
        return scheduleList;
    }
}
