package net.thumbtack.school.hospital.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Schedule;
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
                //() -> assertTrue(scheduleList.get(0).getDoctor().equals(scheduleList2.get(0).getDoctor())),
                () -> assertEquals(scheduleList.get(0).getRoom(), scheduleList2.get(0).getRoom()),
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
        //Patient patienet = scheduleList2.get(0).getPatient();
        //assertEquals("patientLogin", patienet.getUser().getLogin());
    }

    @Test
    public void testGetScheduleBySpeciality() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<Schedule> scheduleList = createTestSchedule(doc, 15, 10);

        scheduleList.get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));


        scheduleDao.createSchedule(scheduleList);

        List<Schedule> scheduleList2 = scheduleDao.getByDoctorSpeciality("spec");

        assertEquals(10, scheduleList2.size());
        //Patient patienet = scheduleList2.get(0).getPatient();
        //assertEquals("patientLogin", patienet.getUser().getLogin());
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


    private List<Schedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            scheduleList.add(new Schedule(
                    doc,
                    new java.sql.Date(System.currentTimeMillis()),
                    Time.valueOf(LocalTime.now().plusMinutes(i * interval)),
                    Time.valueOf(LocalTime.now().plusMinutes((i + 1) * interval - 1)),
                    doc.getRoom()
            ));
        }
        return scheduleList;
    }
}
