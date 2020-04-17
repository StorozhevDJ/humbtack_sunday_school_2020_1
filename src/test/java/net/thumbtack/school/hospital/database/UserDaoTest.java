package net.thumbtack.school.hospital.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.User;

public class UserDaoTest extends DatabasePrepare {

	@Test
	public void testGetUserByLogin() {
		User user = null;
		try {
			user = userDao.getByLogin("admin", "admin");
		} catch (ServerException e) {
			fail();
		}
		User finalUser = user;
		assertAll(() -> assertNotEquals(0, finalUser.getId(), "User id = 0"),
				() -> assertEquals(UserType.ADMINISTRATOR, finalUser.getType(), "User type is not admin"),
				() -> assertEquals("FirstNameAdmin", finalUser.getFirstName(), "User firstName is not FirstNameAdmin"),
				() -> assertEquals("lastNameAdmin", finalUser.getLastName(), "User lastname is not lastNameAdmin"),
				() -> assertNull(finalUser.getPatronymic(), "User patronymic is not null"),
				() -> assertEquals("admin", finalUser.getLogin()),
				() -> assertNull(finalUser.getPassword(), "User password is not null"));
	}

	@Test
	public void testLogInLogOut() {
		assertAll(
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User("admin", "errorPass", new Session("token")))),
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User("errorUser", "admin", new Session("token")))),
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User("admin", "", new Session("token")))),
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User("", "errorPass", new Session("token")))),
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User(null, null, new Session("token")))),
				() -> assertThrows(ServerException.class, () -> userDao.logIn(new User("admin", "Admin", new Session("token")))),
				() -> userDao.logIn(new User("admin", "admin", new Session("token0"))),
				() -> userDao.logIn(new User("AdMin", "admin", new Session("token"))),
				() -> userDao.logOut(new Session("errorToken")),
				() -> userDao.logOut(new Session("")),
				() -> userDao.logOut(null),
				() -> userDao.logOut(new Session("token"))
		);
	}
}
