package net.thumbtack.school.hospital.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.CommonDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.daoimpl.AdminDaoImpl;
import net.thumbtack.school.hospital.database.daoimpl.CommonDaoImpl;
import net.thumbtack.school.hospital.database.daoimpl.UserDaoImpl;
import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.utils.MyBatisUtils;

public class DatabaseTest {

    private CommonDao commonDao = new CommonDaoImpl();
    protected AdminDao adminDao = new AdminDaoImpl();
    protected UserDao userDao = new UserDaoImpl();

    @BeforeAll()
    public static void setUp() {
        boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
        if (!initSqlSessionFactory) {
            throw new RuntimeException("Can't create connection, stop");
        }
    }

    @BeforeEach
    public void clearDB() {
        commonDao.clear();
        User user = new User("FirstNameAdmin", "lastNameAdmin", null, "admin", "admin", null);
        Admin admin = new Admin(user, "Superadmin");
        adminDao.insert(admin);
    }

}
