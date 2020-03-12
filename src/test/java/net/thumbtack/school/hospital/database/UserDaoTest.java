package net.thumbtack.school.hospital.database;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.User;

public class UserDaoTest extends DatabaseTest {

    @Test
    public void testGetUserByLogin() {
        User user = userDao.getByLogin("admin", "admin");
        assertAll(() -> assertNotEquals(0, user.getId(), "User id = 0"),
                () -> assertEquals("admin", user.getType(), "User type is not admin"),
                () -> assertEquals("FirstNameAdmin", user.getFirstName(), "User firstName is not FirstNameAdmin"),
                () -> assertEquals("lastNameAdmin", user.getLastName(), "User lastname is not lastNameAdmin"),
                () -> assertNull(user.getPatronymic(), "User patronymic is not null"),
                () -> assertEquals("admin", user.getLogin()),
                () -> assertNull(user.getPassword(), "User password is not null"));
    }
}
