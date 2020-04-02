package net.thumbtack.school.hospital.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
				() -> assertEquals(User.Type.ADMINISTRATOR, user.getType(), "User type is not admin"),
				() -> assertEquals("FirstNameAdmin", user.getFirstName(), "User firstName is not FirstNameAdmin"),
				() -> assertEquals("lastNameAdmin", user.getLastName(), "User lastname is not lastNameAdmin"),
				() -> assertNull(user.getPatronymic(), "User patronymic is not null"),
				() -> assertEquals("admin", user.getLogin()),
				() -> assertNull(user.getPassword(), "User password is not null"));
	}

	@Test
	public void testLogInLogOut() {
		assertAll(() -> assertFalse(userDao.logIn(new User("admin", "errorPass", new User.Session("token")))),
				() -> assertFalse(userDao.logIn(new User("errorUser", "admin", new User.Session("token")))),
				() -> assertFalse(userDao.logIn(new User("admin", "", new User.Session("token")))),
				() -> assertFalse(userDao.logIn(new User("", "errorPass", new User.Session("token")))),
				() -> assertFalse(userDao.logIn(new User(null, null, new User.Session("token")))),
				() -> assertFalse(userDao.logIn(new User("admin", "Admin", new User.Session("token")))),
				() -> assertTrue(userDao.logIn(new User("admin", "admin", new User.Session("token0")))),
				() -> assertTrue(userDao.logIn(new User("AdMin", "admin", new User.Session("token")))),
				() -> assertFalse(userDao.logOut(new User.Session("errorToken"))),
				() -> assertFalse(userDao.logOut(new User.Session(""))),
				() -> assertFalse(userDao.logOut(null)),
				() -> assertTrue(userDao.logOut(new User.Session("token")))
		);
	}
}
