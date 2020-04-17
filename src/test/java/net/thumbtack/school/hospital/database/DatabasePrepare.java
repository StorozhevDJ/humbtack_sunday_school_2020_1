package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import net.thumbtack.school.hospital.database.utils.MyBatisUtils;

public class DatabasePrepare {

    private CommonDao commonDao = new CommonDaoImpl();
    protected UserDao userDao = new UserDaoImpl();
    protected AdminDao adminDao = new AdminDaoImpl();
    protected DoctorDao doctorDao = new DoctorDaoImpl();
    protected PatientDao patientDao = new PatientDaoImpl();
    protected DayScheduleDao scheduleDao = new DayScheduleDaoImpl();
    protected TicketScheduleDao ticketScheduleDao = new TicketScheduleDaoImpl();

    @BeforeAll()
    public static void setUp() {
        boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
        if (!initSqlSessionFactory) {
            throw new RuntimeException("Can't create connection, stop");
        }
    }

    @BeforeEach
    public void clearDB() {
        commonDao.clear();
        User user = new User("FirstNameAdmin", "lastNameAdmin", null, "admin", "admin", new Session());
        adminDao.insert(new Admin(user, "Superadmin"));

        doctorDao.insert(new Doctor(new User("FirstNameDoc", "lastNameDoc", null, "doc", "doctor", new Session()), new Speciality("spec"), new Room("1")));

        patientDao.insert(new Patient(
                new User("FirstNamePatient", "lastNamePatient", "partronymicPatient", "patientLogin", "passwordPatient", new Session()),
                "patient@mail",
                "addrPatient",
                "+79001112233"));
    }

}
