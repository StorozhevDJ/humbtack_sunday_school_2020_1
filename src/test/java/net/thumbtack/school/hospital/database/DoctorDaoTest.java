package net.thumbtack.school.hospital.database;

import java.util.List;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class})
public class DoctorDaoTest extends DatabasePrepare {

    private UserDao userDao;

    @Autowired
    public DoctorDaoTest(CommonDao commonDao, UserDao userDao, AdminDao adminDao, DoctorDao doctorDao, PatientDao patientDao) {
        super(commonDao, adminDao, doctorDao, patientDao);
        this.userDao = userDao;
    }

    @Test
    public void testAddDoctor() throws ServerException {
        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        //assertThrows(ServerException.class, () -> doctorDao.insert(new Doctor(user, new Speciality("ErrorSpeciality"), new Room("room"))));
        Doctor doc = new Doctor(user, new Speciality("spec"), new Room("6"));
        doctorDao.insert(doc);
        assertThrows(ServerException.class, () -> doctorDao.insert(doc));// Check double add
        assertAll(
                () -> assertNotEquals(0, doc.getId()),
                () -> assertEquals(doc.getUser().getId(), user.getId()),
                () -> assertEquals(UserType.DOCTOR, doc.getUser().getType()),
                () -> assertEquals(2, doctorDao.getCount())
        );
    }

    @Test
    public void testGetByToken() throws ServerException {
        try {
            userDao.logIn(new User("doc", "doctor", new Session("token")));
        } catch (ServerException e) {
            fail();
        }

        Doctor doc = doctorDao.getByToken(new Session("token"));
        assertAll(
                () -> assertEquals("spec", doc.getSpeciality().getName()),
                () -> assertEquals("1", doc.getRoom().getNumber()),
                () -> assertEquals(UserType.DOCTOR, doc.getUser().getType(), "User type is not doctor"),
                () -> assertEquals("FirstNameDoc", doc.getUser().getFirstName(), "User firstName is not FirstNameDoc"),
                () -> assertEquals("lastNameDoc", doc.getUser().getLastName(), "User lastname is not lastNameDoc"),
                () -> assertNull(doc.getUser().getPatronymic(), "User patronymic is not null"),
                () -> assertEquals("doc", doc.getUser().getLogin(), "User login is not doc"),
                () -> assertNull(doc.getUser().getPassword(), "User password is not null"),
                () -> assertNull(doctorDao.getByToken(new Session("errorToken"))),
                () -> assertNull(doctorDao.getByToken(new Session(""))),
                () -> assertNull(doctorDao.getByToken(new Session("   "))),
                () -> assertNull(doctorDao.getByToken(new Session(" token "))),
                () -> assertNull(doctorDao.getByToken(null))
        );
    }

    @Test
    public void testGetByDoctorId() throws ServerException {
        List<Doctor> docList = doctorDao.getBySpeciality("spec");

        Doctor doc = doctorDao.getByDoctorId(docList.get(0).getId());

        assertAll(
                () -> assertEquals("spec", doc.getSpeciality().getName()),
                () -> assertEquals("1", doc.getRoom().getNumber()),
                () -> assertEquals(UserType.DOCTOR, doc.getUser().getType(), "User type is not doctor"),
                () -> assertEquals("FirstNameDoc", doc.getUser().getFirstName(), "User firstName is not FirstNameDoc"),
                () -> assertEquals("lastNameDoc", doc.getUser().getLastName(), "User lastname is not lastNameDoc"),
                () -> assertNull(doc.getUser().getPatronymic(), "User patronymic is not null"),
                () -> assertEquals("doc", doc.getUser().getLogin(), "User login is not doc"),
                () -> assertNull(doc.getUser().getPassword(), "User password is not null"),
                () -> assertNull(doctorDao.getByDoctorId(0)),
                () -> assertNull(doctorDao.getByDoctorId(123456))
        );
    }

    @Test
    public void testGetBySpeciality() throws ServerException {
        List<Doctor> docList = doctorDao.getBySpeciality("spec");

        assertEquals(1, docList.size());
        Doctor doc = docList.get(0);

        assertAll(
                () -> assertEquals("spec", doc.getSpeciality().getName()),
                () -> assertEquals("1", doc.getRoom().getNumber()),
                () -> assertEquals(UserType.DOCTOR, doc.getUser().getType(), "User type is not doctor"),
                () -> assertEquals("FirstNameDoc", doc.getUser().getFirstName(), "User firstName is not FirstNameDoc"),
                () -> assertEquals("lastNameDoc", doc.getUser().getLastName(), "User lastname is not lastNameDoc"),
                () -> assertNull(doc.getUser().getPatronymic(), "User patronymic is not null"),
                () -> assertNull(doc.getUser().getLogin(), "User login is not null"),
                () -> assertNull(doc.getUser().getPassword(), "User password is not null"),
                () -> assertTrue(doctorDao.getBySpeciality(null).isEmpty()),
                () -> assertTrue(doctorDao.getBySpeciality("123456").isEmpty())
        );
    }

    @Test
    public void testGetCount() throws ServerException {
        assertEquals(1, doctorDao.getCount());
    }

}
