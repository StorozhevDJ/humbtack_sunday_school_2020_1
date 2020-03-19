package net.thumbtack.school.hospital.database.daoimpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.model.Doctor;

public class DoctorDaoImpl extends DaoImplBase implements DoctorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    @Override
    public Doctor insert(Doctor doctor) throws RuntimeException {
        LOGGER.debug("DAO insert Doctor {}", doctor);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(doctor.getUser());
                if (getDoctorMapper(sqlSession).insert(doctor) == 0) {
                    throw new RuntimeException("Error, no row inserted");
                }
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
        LOGGER.debug("DAO get Doctor by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor by token {} {}", token, ex);
            throw ex;
        }
    }

    @Override
    public Doctor getByUserId(int id) {
        LOGGER.debug("DAO get Doctor by User id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getByUserId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor by User id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public Doctor getByDoctorId(int id) {
        LOGGER.debug("DAO get Doctor by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getByDoctorId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public List<Doctor> getBySpeciality(String speciality) {
        LOGGER.debug("DAO get All Doctor with speciality {}", speciality);
        try (SqlSession sqlSession = getSession()) {
            return getDoctorMapper(sqlSession).getBySpeciality(speciality);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Doctor with speciality {} {}", speciality, ex);
            throw ex;
        }
    }

    @Override
    public int getCount() {
        LOGGER.debug("DAO get Doctors count");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getDoctorMapper(sqlSession).getCount();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get Doctors count {} ", ex);
                throw ex;
            }
        }
    }


}
