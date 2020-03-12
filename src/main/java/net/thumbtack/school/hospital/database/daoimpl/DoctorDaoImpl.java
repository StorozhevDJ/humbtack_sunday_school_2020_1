package net.thumbtack.school.hospital.database.daoimpl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.model.Doctor;

public class DoctorDaoImpl extends DaoImplBase implements DoctorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    @Override
    public Doctor insert(Doctor doctor) {
        LOGGER.debug("DAO insert Doctor {}", doctor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(doctor.getUser());
                getDoctorMapper(sqlSession).insert(doctor);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Doctor {} {}", doctor, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return doctor;
    }

    @Override
    public Doctor getByToken(String token) {
        LOGGER.debug("DAO get User by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get User by token {} {}", token, ex);
            throw ex;
        }
    }

    @Override
    public Doctor getById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

}
