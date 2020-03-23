package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Doctor;
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
                () -> assertEquals(scheduleList.get(0).getDoctorId(), scheduleList2.get(0).getDoctorId()),
                () -> assertEquals(scheduleList.get(0).getRoom(), scheduleList2.get(0).getRoom()),
                () -> assertNull(scheduleList.get(0).getPatientId()),
                () -> assertNull(scheduleList.get(0).getTicket()),
                () -> assertEquals(8, scheduleList2.size())
        );
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
        //List<Schedule> scheduleList = scheduleDao.getByDoctorId(id);

    }

    @Test
    public void testGetScheduleBySpeciality() {
        Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<Schedule> scheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(scheduleList);

        List<Schedule> scheduleList2 = scheduleDao.getByDoctorSpeciality("spec");

        assertEquals(8, scheduleList2.size());

    }


    private List<Schedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<Schedule> scheduleList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            scheduleList.add(new Schedule(
                    doc.getId(),
                    new java.sql.Date(System.currentTimeMillis()),
                    Time.valueOf(LocalTime.now().plusMinutes(i * interval)),
                    Time.valueOf(LocalTime.now().plusMinutes((i + 1) * interval - 1)),
                    doc.getRoom()
            ));
        }
        return scheduleList;
    }

}
