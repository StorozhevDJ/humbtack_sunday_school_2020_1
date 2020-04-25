package net.thumbtack.school.hospital.database;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.User;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class})
public class PatientDaoTest extends DatabasePrepare {

    private UserDao userDao;

    @Autowired
    public PatientDaoTest(CommonDao commonDao,
                       UserDao userDao,
                       AdminDao adminDao,
                       DoctorDao doctorDao,
                       PatientDao patientDao) {
        super(commonDao, adminDao, doctorDao, patientDao);
        this.userDao = userDao;
    }

    @Test
    public void testGetCount() throws ServerException {
        assertEquals(1, patientDao.getCount());
    }

    @Test
    public void testAddPatient() throws ServerException {
        User user = new User("Вася", "Петечкин", "Васильевич", "testPatient", "password", null);
        Patient patient = new Patient(user, "e@mail", "Addr", "79012345678");
        patientDao.insert(patient);
        assertThrows(ServerException.class, () -> patientDao.insert(patient));// Check double add
        assertAll(
                () -> assertNotEquals(0, patient.getId()),
                () -> assertEquals(patient.getUser().getId(), user.getId()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType()),
                () -> assertEquals(2, patientDao.getCount())
        );
    }

    @Test
    public void testGetByToken() throws ServerException {
        try {
            userDao.logIn(new User("patientLogin", "passwordPatient", new Session("token")));
        } catch (ServerException e) {
            fail();
        }

        Patient patient = patientDao.getByToken(new Session("token"));
        assertAll(
                () -> assertEquals("patient@mail", patient.getEmail()),
                () -> assertEquals("addrPatient", patient.getAddress()),
                () -> assertEquals("+79001112233", patient.getPhone()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", patient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", patient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", patient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", patient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertEquals("token", patient.getUser().getSession().getToken(), "User token is not token"),
                () -> assertNull(patient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByToken(new Session("errorToken"))),
                () -> assertNull(patientDao.getByToken(new Session(""))),
                () -> assertNull(patientDao.getByToken(new Session("   "))),
                () -> assertNull(patientDao.getByToken(new Session(" token "))),
                () -> assertNull(patientDao.getByToken(null))
        );
    }

    @Test
    public void testGetByUserId() {
        Patient patient = null;
        try {
            patient = patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId());
        } catch (ServerException e) {
            fail();
        }

        Patient finalPatient = patient;
        assertAll(
                () -> assertEquals("patient@mail", finalPatient.getEmail()),
                () -> assertEquals("addrPatient", finalPatient.getAddress()),
                () -> assertEquals("+79001112233", finalPatient.getPhone()),
                () -> assertEquals(UserType.PATIENT, finalPatient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", finalPatient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", finalPatient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", finalPatient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", finalPatient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertNull(finalPatient.getUser().getSession().getToken(), "User token is not null"),
                () -> assertNull(finalPatient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByPatientId(0)),
                () -> assertNull(patientDao.getByPatientId(123456))
        );
    }

    @Test
    public void testGetByPatientId() throws ServerException {
        int id = 0;
        try {
            id = patientDao.getByUserId(userDao.getByLogin("patientLogin", "passwordPatient").getId()).getId();
        } catch (ServerException e) {
            fail();
        }

        Patient patient = patientDao.getByPatientId(id);

        assertAll(
                () -> assertEquals("patient@mail", patient.getEmail()),
                () -> assertEquals("addrPatient", patient.getAddress()),
                () -> assertEquals("+79001112233", patient.getPhone()),
                () -> assertEquals(UserType.PATIENT, patient.getUser().getType(), "User type is not patient"),
                () -> assertEquals("FirstNamePatient", patient.getUser().getFirstName(), "User firstName is not FirstNamePatient"),
                () -> assertEquals("lastNamePatient", patient.getUser().getLastName(), "User lastname is not lastNamePatient"),
                () -> assertEquals("partronymicPatient", patient.getUser().getPatronymic(), "User patronymic is not partronymicPatient"),
                () -> assertEquals("patientLogin", patient.getUser().getLogin(), "User login is not patientLogin"),
                () -> assertNull(patient.getUser().getSession(), "User token is not null"),
                () -> assertNull(patient.getUser().getPassword(), "User password is not null"),
                () -> assertNull(patientDao.getByPatientId(0)),
                () -> assertNull(patientDao.getByPatientId(123456))
        );
    }

}
