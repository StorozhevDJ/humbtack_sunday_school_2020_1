package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Schedule;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ScheduleDaoTest extends DatabaseTest {

    @Test
    public void testCreateSchedule() {

        List<Schedule> scheduleList = new ArrayList<>();
        /*scheduleList.add(new Schedule(
                //1,
                "ticket",
                "room1",
                new java.sql.Date(System.currentTimeMillis()),
                Time.valueOf( LocalTime.now() ),
                Time.valueOf( LocalTime.now() ),
                1
        ));*/
        int insertedCnt = scheduleDao.createSchedule(scheduleList);
    	
        /*User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality")));
        Doctor doc = new Doctor(user, "spec");
        doctorDao.insert(doc);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(doc));// Check double add
        assertThrows(RuntimeException.class, () -> doctorDao.insert(null));
        assertAll(
                () -> assertNotEquals(0, doc.getId()),
                () -> assertEquals(doc.getUser().getId(), user.getId()),
                () -> assertEquals("doctor", doc.getUser().getType()),
                () -> assertEquals(2, doctorDao.getCount())
        );*/
    }

    @Test
    public void testSetAppointment() {

    }

    @Ignore
    @Test
    public void testGetAllSchedule() {
        //Schedule schedule = scheduleDao.getAllSchedule();
    }

    @Test
    public void testGetDoctorSchedule() {


    }

    @Test
    public void testGetSpecialitySchedule() {


    }

}
