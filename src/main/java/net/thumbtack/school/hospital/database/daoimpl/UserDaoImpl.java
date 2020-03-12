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


}
