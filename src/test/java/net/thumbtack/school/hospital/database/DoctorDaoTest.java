package net.thumbtack.school.hospital.database;

import java.util.List;

import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorDaoTest extends DatabasePrepare {

    @Test
    public void testAddDoctor() {
        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, new Speciality("ErrorSpeciality"), new Room("room"))));
        Doctor doc = new Doctor(user, new Speciality("spec"), new Room("6"));
        doctorDao.insert(doc);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(doc));// Check double add
        assertThrows(RuntimeException.class, () -> doctorDao.insert(null));
        assertAll(
                () -> assertNotEquals(0, doc.getId()),
                () -> assertEquals(doc.getUser().getId(), user.getId()),
                () -> assertEquals(UserType.DOCTOR, doc.getUser().getType()),
                () -> assertEquals(2, doctorDao.getCount())
        );
    }

    @Test
    public void testGetByToken() {
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
    public void testGetByDoctorId() {
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
    public void testGetBySpeciality() {
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
    public void testGetCount() {
        assertEquals(1, doctorDao.getCount());
    }

}
