package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.User;

public class DoctorDaoTest extends DatabaseTest {

    @Test
    public void testAddDoctor() {
        User user = new User("Вася", "Петечкин", "Васильевич", "testDoc", "password", null);
        assertThrows(RuntimeException.class, () -> doctorDao.insert(new Doctor(user, "ErrorSpeciality")));
        Doctor doc = new Doctor(user, "spec");
        doctorDao.insert(doc);
        // Check double add
        assertThrows(RuntimeException.class, () -> doctorDao.insert(doc));
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
                //() -> assertEquals("spec", doc.getSpeciality()),
                () -> assertEquals("admin", doc.getUser().getType(), "User type is not doctor"),
                () -> assertEquals("FirstNameDoc", doc.getUser().getFirstName(), "User firstName is not FirstNameDoc"),
                () -> assertEquals("lastNameDoc", doc.getUser().getLastName(), "User lastname is not lastNameDoc"),
                () -> assertNull(doc.getUser().getPatronymic(), "User patronymic is not null"),
                () -> assertEquals("doc", doc.getUser().getLogin(), "User login is not doc"),
                () -> assertNull(doc.getUser().getPassword(), "User password is not null")
        );
    }
    
    
        /*Admin getAdmin = adminDao.getByUserId(user.getId());
        assertAll(
                () -> assertNotEquals(0, getAdmin.getId(), "admin id = 0"),
                () -> assertEquals(getAdmin.getUser().getId(), user.getId(), "Admin and user IDs is different"),
                () -> assertEquals("SecondAdmin", getAdmin.getPosition(), "Admin position is not SecondAdmin"),
                () -> assertEquals("admin", getAdmin.getUser().getType(), "User type is not admin"),
                () -> assertEquals("Вася", getAdmin.getUser().getFirstName(), "User firstName is not Вася"),
                () -> assertEquals("Петечкин", getAdmin.getUser().getLastName(), "User lastname is not Петечкин"),
                () -> assertEquals("Васильевич", getAdmin.getUser().getPatronymic(), "User patronymic is not Васильевич"),
                () -> assertEquals("testAdmin", getAdmin.getUser().getLogin(), "User login is not testAdmin"),
                () -> assertNull(getAdmin.getUser().getPassword(), "User password is not null")
        );*/


}
