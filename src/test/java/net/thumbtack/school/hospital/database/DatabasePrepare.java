package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import net.thumbtack.school.hospital.database.utils.MyBatisUtils;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class})
public abstract class DatabasePrepare {

    private CommonDao commonDao;
    protected AdminDao adminDao;
    protected DoctorDao doctorDao;
    protected PatientDao patientDao;

    @Autowired
    public DatabasePrepare(CommonDao commonDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao) {
        this.commonDao = commonDao;
        this.adminDao = adminDao;
        this.doctorDao = doctorDao;
        this.patientDao = patientDao;
    }

    /*@BeforeAll()
    public static void setUp() {
        boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
        if (!initSqlSessionFactory) {
            throw new RuntimeException("Can't create connection, stop");
        }
    }*/

    @BeforeEach
    public void clearDB() throws ServerException {
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
