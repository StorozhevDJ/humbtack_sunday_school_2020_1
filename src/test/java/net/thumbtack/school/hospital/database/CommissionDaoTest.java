package net.thumbtack.school.hospital.database;

public class CommissionDaoTest {
    /*@Test
    public void testAddCommission() {
        Doctor doc1 = doctorDao.getBySpeciality("spec").get(0);

        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality", "room")));
        Doctor doc2 = new Doctor(user, "spec", "6");
        doctorDao.insert(doc2);

        List<Schedule> scheduleList = createTestSchedule(doc1, 15, 8);
        scheduleDao.createSchedule(scheduleList);

        Schedule schedule = scheduleList.get(5);
        schedule.getDaySchedule().setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.getDaySchedule().setTicket("testTicket");

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
        schedule.getDaySchedule().setPatient(patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()));
        schedule.getDaySchedule().setTicket("testTicket");

        assertTrue(scheduleDao.addTicket(schedule));

        List<Commission> commissionList = new ArrayList<>();
        commissionList.add(new Commission(schedule.getId(), doc2.getId()));

        assertEquals(1, scheduleDao.addCommission(commissionList));

        scheduleList = scheduleDao.getByDoctorId(doc1.getId());
        assertNotNull(scheduleList.get(0).getCommission());
    }*/
}
