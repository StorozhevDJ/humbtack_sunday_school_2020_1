package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class, ScheduleDaoImpl.class})
public class DayScheduleDaoTest extends DatabasePrepare {

    private UserDao userDao;
    private ScheduleDao scheduleDao;

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

        /*Doctor doc = doctorDao.getBySpeciality("spec").get(0);
        List<DaySchedule> dayScheduleList = createTestSchedule(doc, 15, 8);

        int insertedCnt = scheduleDao.createSchedule(dayScheduleList);
        assertEquals(8, insertedCnt);

        List<DaySchedule> dayScheduleList2 = scheduleDao.getByDoctorId(doc.getId());

        List<TicketSchedule> ts = ticketScheduleDao.getDayScheduleById(dayScheduleList2.get(0).getId());

        assertAll(
                () -> assertNotEquals(0, ts.get(0).getId()),
                //() -> assertNull(scheduleList.get(0).getDaySchedule().get(0).getPatient()),
                //() -> assertNull(scheduleList.get(0).getDaySchedule().get(0).getTicket()),
                () -> assertEquals(8, ts.size())
        );*/
    }

    /*@Test
    public void testGetAllSchedule() throws ServerException {
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
    public void testGetScheduleBySpeciality() throws ServerException {
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

        //assertTrue(scheduleDao.addTicket(schedule.getDaySchedule().get(0)));
        //assertFalse(scheduleDao.addTicket(schedule));
    }


    private List<DaySchedule> createTestSchedule(Doctor doc, int interval, int count) {
        List<DaySchedule> dayScheduleList = new ArrayList<>();

        List<TicketSchedule> ticketSchedule = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            ticketSchedule.add(
                    new TicketSchedule(
                            "ticket",
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
    }*/
}
