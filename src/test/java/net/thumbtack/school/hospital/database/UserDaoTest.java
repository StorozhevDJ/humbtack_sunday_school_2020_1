package net.thumbtack.school.hospital.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.daoimpl.*;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.junit.jupiter.api.Test;

import net.thumbtack.school.hospital.database.model.User;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CommonDaoImpl.class, UserDaoImpl.class, AdminDaoImpl.class, DoctorDaoImpl.class, PatientDaoImpl.class})
public class UserDaoTest extends DatabasePrepare {

	private UserDao userDao;

	@Autowired
	public UserDaoTest(CommonDao commonDao,
					  UserDao userDao,
					  AdminDao adminDao,
					  DoctorDao doctorDao,
					  PatientDao patientDao) {
		super(commonDao, adminDao, doctorDao, patientDao);
		this.userDao = userDao;
	}

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
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User("admin", "errorPass", new Session("token")))),
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User("errorUser", "admin", new Session("token")))),
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User("admin", "", new Session("token")))),
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User("", "errorPass", new Session("token")))),
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User(null, null, new Session("token")))),
				() -> assertThrows(Exception.class, () -> userDao.logIn(new User("admin", "Admin", new Session("token")))),
				() -> userDao.getByLogin("admin", "admin"),
				() -> userDao.getByLogin("AdMin", "admin"),
				() -> userDao.logOut(new Session("errorToken")),
				() -> userDao.logOut(new Session("")),
				() -> userDao.logOut(null),
				() -> userDao.logOut(new Session("token"))
		);
	}
}
