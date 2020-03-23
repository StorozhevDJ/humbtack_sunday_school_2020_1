package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.User;

public class DoctorDaoTest extends DatabaseTest {

    @Test
    public void testAddDoctor() {
        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality", "room")));
        Doctor doc = new Doctor(user, "spec", "6");
        doctorDao.insert(doc);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(doc));// Check double add
        assertThrows(RuntimeException.class, () -> doctorDao.insert(null));
        assertAll(
                () -> assertNotEquals(0, doc.getId()),
                () -> assertEquals(doc.getUser().getId(), user.getId()),
                () -> assertEquals("doctor", doc.getUser().getType()),
                () -> assertEquals(2, doctorDao.getCount())
        );
    }

    @Test
    public void testGetByToken() {
        assertTrue(userDao.logIn(new User("doc", "doctor", "token")));

        Doctor doc = doctorDao.getByToken("token");
        assertAll(
                () -> assertEquals("spec", doc.getSpeciality()),
                () -> assertEquals("1", doc.getRoom()),
                () -> assertEquals("doctor", doc.getUser().getType(), "User type is not doctor"),
                () -> assertEquals("FirstNameDoc", doc.getUser().getFirstName(), "User firstName is not FirstNameDoc"),
                () -> assertEquals("lastNameDoc", doc.getUser().getLastName(), "User lastname is not lastNameDoc"),
                () -> assertNull(doc.getUser().getPatronymic(), "User patronymic is not null"),
                () -> assertEquals("doc", doc.getUser().getLogin(), "User login is not doc"),
                () -> assertNull(doc.getUser().getPassword(), "User password is not null"),
                () -> assertNull(doctorDao.getByToken("errorToken")),
                () -> assertNull(doctorDao.getByToken("")),
                () -> assertNull(doctorDao.getByToken("   ")),
                () -> assertNull(doctorDao.getByToken(" token ")),
                () -> assertNull(doctorDao.getByToken(null))
        );
    }

    @Test
    public void testGetByDoctorId() {
        List<Doctor> docList = doctorDao.getBySpeciality("spec");

        Doctor doc = doctorDao.getByDoctorId(docList.get(0).getId());

        assertAll(
                () -> assertEquals("spec", doc.getSpeciality()),
                () -> assertEquals("1", doc.getRoom()),
                () -> assertEquals("doctor", doc.getUser().getType(), "User type is not doctor"),
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
                () -> assertEquals("spec", doc.getSpeciality()),
                () -> assertEquals("1", doc.getRoom()),
                () -> assertEquals("doctor", doc.getUser().getType(), "User type is not doctor"),
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
