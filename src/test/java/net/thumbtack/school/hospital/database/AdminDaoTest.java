package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.User;


public class AdminDaoTest extends DatabaseTest {

    @Test
    public void testAddAdmin() {
        User user = new User("Вася", "Петечкин", "Васильевич", "admin2", "password", null);
        Admin admin = new Admin(user, "SecondAdmin");
        adminDao.insert(admin);
        // Check double add
        assertThrows(RuntimeException.class, () -> adminDao.insert(admin));
        assertAll(
                () -> assertNotEquals(0, admin.getId()),
                () -> assertEquals(admin.getUser().getId(), user.getId()),
                () -> assertEquals("admin", admin.getUser().getType())
        );

        Admin getAdmin = adminDao.getByUserId(user.getId());
        assertAll(
                () -> assertNotEquals(0, getAdmin.getId(), "admin id = 0"),
                () -> assertEquals(getAdmin.getUser().getId(), user.getId(), "Admin and user IDs is different"),
                () -> assertEquals("SecondAdmin", getAdmin.getPosition(), "Admin position is not SecondAdmin"),
                () -> assertEquals("admin", getAdmin.getUser().getType(), "User type is not admin"),
                () -> assertEquals("Вася", getAdmin.getUser().getFirstName(), "User firstName is not Вася"),
                () -> assertEquals("Петечкин", getAdmin.getUser().getLastName(), "User lastname is not Петечкин"),
                () -> assertEquals("Васильевич", getAdmin.getUser().getPatronymic(), "User patronymic is not Васильевич"),
                () -> assertEquals("admin2", getAdmin.getUser().getLogin(), "User login is not admin2"),
                () -> assertNull(getAdmin.getUser().getPassword(), "User password is not null")
        );
    }

    @Test
    public void testGetDefaultAdmin() {
        User user = userDao.getByLogin("admin", "admin");
        Admin admin = adminDao.getByUserId(user.getId());
        assertTrue(admin.getId() != 0);
        assertTrue(admin.getUser().getId() != 0);
        assertTrue(admin.getUser().getId() != 0);
    }


}
