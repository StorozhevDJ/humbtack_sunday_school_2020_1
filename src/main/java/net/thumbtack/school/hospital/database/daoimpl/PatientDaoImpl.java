package net.thumbtack.school.hospital.database.daoimpl;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.model.Patient;

public class PatientDaoImpl extends DaoImplBase implements PatientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    @Override
    public Patient insert(Patient patient) {
        LOGGER.debug("DAO insert Patient {}", patient);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).insert(patient.getUser());
                if (getPatientMapper(sqlSession).insert(patient) == 0) {
                    throw new RuntimeException("Error, no row with Patient inserted");
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Patient {} {}", patient, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return patient;
    }

    @Override
    public Patient getByPatientId(int id) {
        LOGGER.debug("DAO get Patient by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).getByPatientId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Patient by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public Patient getByUserId(int id) {
        LOGGER.debug("DAO get Patient by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).getByUserId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Patient by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public Patient getByToken(Session token) {
        LOGGER.debug("DAO get Patient by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).getByToken(token);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Patient by token {} {}", token, ex);
            throw ex;
        }
    }

    @Override
    public List<Patient> getAllPatientByDoctorId(int id) {
        LOGGER.debug("DAO get All Patient by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getPatientMapper(sqlSession).getByDoctorId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Patient by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public int getCount() {
        LOGGER.debug("DAO get Patients count");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getPatientMapper(sqlSession).getCount();
            } catch (RuntimeException ex) {
                LOGGER.info("Can't get Patients count {} ", ex);
                throw ex;
            }
        }
    }


}
