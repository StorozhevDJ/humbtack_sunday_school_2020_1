package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.mappers.PatientMapper;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Statistic;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class PatientDaoImpl implements PatientDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    private PatientMapper patientMapper;
    private UserMapper userMapper;

    public PatientDaoImpl() {
    }

    @Autowired
    public PatientDaoImpl(UserMapper userMapper, PatientMapper patientMapper) {
        this.userMapper = userMapper;
        this.patientMapper = patientMapper;
    }

    @Override
    @Transactional(rollbackFor = ServerException.class)
    public Patient insert(Patient patient) throws ServerException {
        LOGGER.debug("DAO insert Patient {}", patient);
        try {
            userMapper.insert(patient.getUser());
            if (patientMapper.insert(patient) == 0) {
                throw new ServerException(ServerError.OTHER_ERROR);
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Patient {} {}", patient, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
        return patient;
    }

    @Override
    public void update(Patient patient) throws ServerException {
        LOGGER.debug("DAO insert Patient {}", patient);
        try {
            userMapper.update(patient.getUser());
            patientMapper.update(patient);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Patient {} {}", patient, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public Patient getByPatientId(int id) throws ServerException {
        LOGGER.debug("DAO get Patient by id {}", id);
        try {
            return patientMapper.getByPatientId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Patient by id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public Patient getByUserId(int id) throws ServerException {
        LOGGER.debug("DAO get Patient by id {}", id);
        try {
            return patientMapper.getByUserId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Patient by id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public Patient getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get Patient by token {}", token);
        try {
            return patientMapper.getByToken(token);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Patient by token {} {}", token, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<Statistic> getTicketCount(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO get Doctors count");
        try {
            return patientMapper.getTicketsCount(id, dateStart, dateEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctors count {} ", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public int getCount() throws ServerException {
        LOGGER.debug("DAO get Patients count");
        try {
            return patientMapper.getCount();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Patients count {} ", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

}
