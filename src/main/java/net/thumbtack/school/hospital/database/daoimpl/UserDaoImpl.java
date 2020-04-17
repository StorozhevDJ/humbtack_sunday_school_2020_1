package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
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
    public User getByToken(Session token) throws ServerException {
        LOGGER.debug("DAO get User by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get User by token {} {}", token, ex);
            throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
        }
    }

    @Override
    public void logIn(User user) throws ServerException {
        LOGGER.debug("DAO LogIn user \"{}\" ", user.getLogin());
        try (SqlSession sqlSession = getSession()) {
            try {
                int userId = getUserMapper(sqlSession).getByLogin(user.getLogin(), user.getPassword()).getId();
                user.setId(userId);
                getUserMapper(sqlSession).insertToken(user);
            } catch (RuntimeException ex) {
                sqlSession.rollback();
                LOGGER.info("Can't LogIn user {} {} ", user.getLogin(), ex);
                throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void logOut(Session token) throws ServerException {
        LOGGER.debug("DAO LogOut user with token {} ", token);

        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteToken(token);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't LogOut user with token {} {} ", token, ex);
                sqlSession.rollback();
                throw new ServerException(ServerError.TOKEN_INVALID);
            }
            sqlSession.commit();
        }
    }


}
