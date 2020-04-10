package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class ScheduleDaoTest extends DatabaseTest {

    @Test
    public void testCreateSchedule() {

        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(scheduleList);
        assertEquals(8, insertedCnt);

        List<Schedule> scheduleList2 = scheduleDao.getByDoctorId(doc.getId());

        /*List<DaySchedule> ds = dayScheduleDao.getDayByScheduleId(scheduleList2.get(0).getId());

        assertAll(
                () -> assertNotEquals(0, scheduleList2.get(1).getId()),
                () -> assertNull(scheduleList.get(0).getDaySchedule().get(0).getPatient()),
                () -> assertNull(scheduleList.get(0).getDaySchedule().get(0).getTicket()),
                () -> assertEquals(8, scheduleList2.size())
        );*/
    }

    @Test
    public void testGetAllSchedule() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 20, 15);
        scheduleList.get(0).getDaySchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        scheduleDao.createSchedule(scheduleList);
        List<Schedule> scheduleList2 = scheduleDao.getAllSchedule();
        assertEquals(15, scheduleList2.size());
    }

    @Test
    public void testGetScheduleBySpeciality() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<Schedule> scheduleList = createTestSchedule(doc, 15, 10);
        scheduleList.get(0).getDaySchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        scheduleDao.createSchedule(scheduleList);
        List<Schedule> scheduleList2 = scheduleDao.getByDoctorSpeciality("spec");
        assertEquals(10, scheduleList2.size());
    }

    @Test
    public void testAddTicket() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 15, 8);
        scheduleDao.createSchedule(scheduleList);

        Schedule schedule = scheduleList.get(0);
        schedule.getDaySchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.getDaySchedule().get(0).setTicket("testTicket");

        //assertTrue(scheduleDao.addTicket(schedule.getDaySchedule().get(0)));
        //assertFalse(scheduleDao.addTicket(schedule));
    }


    private List<Schedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<Schedule> scheduleList = new ArrayList<>();

        List<DaySchedule> daySchedule = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            daySchedule.add(
                    new DaySchedule("ticket",
                            LocalTime.now().plusMinutes(i * interval),
                            LocalTime.now().plusMinutes((i + 1) * interval - 1),
                            new Patient()
                    ));
        }
        scheduleList.add(new Schedule(
                doc,
                LocalDate.now(),
                daySchedule
        ));
        return scheduleList;
    }
}
