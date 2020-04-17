package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class DayScheduleDaoTest extends DatabasePrepare {

    @Test
    public void testCreateSchedule() {

        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(dayScheduleList);
        assertEquals(8, insertedCnt);

        List<DaySchedule> dayScheduleList2 = scheduleDao.getByDoctorId(doc.getId());

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
        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 20, 15);
        try {
            dayScheduleList.get(0).getTicketSchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        } catch (ServerException e) {
            fail();
        }
        scheduleDao.createSchedule(dayScheduleList);
        List<DaySchedule> dayScheduleList2 = scheduleDao.getAllSchedule();
        assertEquals(15, dayScheduleList2.size());
    }

    @Test
    public void testGetScheduleBySpeciality() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);

        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 10);
        try {
            dayScheduleList.get(0).getTicketSchedule().get(0).setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        } catch (ServerException e) {
            fail();
        }
        scheduleDao.createSchedule(dayScheduleList);
        List<DaySchedule> dayScheduleList2 = scheduleDao.getByDoctorSpeciality("spec");
        assertEquals(10, dayScheduleList2.size());
    }

    @Test
    public void testAddTicket() {
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

        //assertTrue(scheduleDao.addTicket(schedule.getDaySchedule().get(0)));
        //assertFalse(scheduleDao.addTicket(schedule));
    }


    private List<DaySchedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<DaySchedule> dayScheduleList = new ArrayList<>();

        List<TicketSchedule> ticketSchedule = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            ticketSchedule.add(
                    new TicketSchedule("ticket",
                            LocalTime.now().plusMinutes(i * interval),
                            LocalTime.now().plusMinutes((i + 1) * interval - 1),
                            new Patient()
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
