package net.thumbtack.school.hospital.database.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.User;

public class UserDaoImpl extends DaoImplBase implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User getByLogin(String login, String password) {
        LOGGER.debug("DAO get User by login {}", login);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getByLogin(login, password);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get User by login {} {}", login, ex);
            throw ex;
        }
    }

    @Override
    public User getByToken(String token) {
        LOGGER.debug("DAO get User by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get User by token {} {}", token, ex);
            throw ex;
        }
    }

    @Override
    public boolean logIn(User user) {
        LOGGER.debug("DAO LogIn user \"{}\" ", user.getLogin());
        int res = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                res = getUserMapper(sqlSession).insertToken(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't LogIn user {} {} ", user.getLogin(), ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
            return res == 1;
        }
    }

    @Override
    public boolean logOut(String token) {
        LOGGER.debug("DAO LogOut user with token {} ", token);
        int res = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                res = getUserMapper(sqlSession).deleteToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't LogOut user with token {} {} ", token, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
            return res == 1;
        }
    }


}
