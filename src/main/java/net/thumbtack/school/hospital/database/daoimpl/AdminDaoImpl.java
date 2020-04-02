package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.model.Admin;


public class AdminDaoImpl extends DaoImplBase implements AdminDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    @Override
    public Admin insert(Admin admin) {
        LOGGER.debug("DAO insert Admin {}", admin);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(admin.getUser());
                getAdminMapper(sqlSession).insert(admin);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Admin {} {}", admin, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return admin;
    }

    @Override
    public Admin getByToken(User.Session token) {
        LOGGER.debug("DAO get Admin by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getAdminMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Admin by token {} {}", token, ex);
            throw ex;
        }
    }

    @Override
    public Admin getByUserId(int id) {
        LOGGER.debug("DAO get Admin by db ID {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getAdminMapper(sqlSession).getByUserId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Admin by db ID {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public int getCount() {
        LOGGER.debug("DAO get admins count");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getAdminMapper(sqlSession).getCount();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get admins count {} ", ex);
                throw ex;
            }
        }
    }


}
