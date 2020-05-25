package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.mappers.DoctorMapper;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
import net.thumbtack.school.hospital.database.model.Doctor;
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
public class DoctorDaoImpl implements DoctorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DoctorDaoImpl.class);

    private UserMapper userMapper;
    private DoctorMapper doctorMapper;

    public DoctorDaoImpl() {
    }

    @Autowired
    public DoctorDaoImpl(UserMapper userMapper, DoctorMapper doctorMapper) {
        this.userMapper = userMapper;
        this.doctorMapper = doctorMapper;
    }

    @Override
    @Transactional(rollbackFor = ServerException.class)
    public Doctor insert(Doctor doctor) throws ServerException {
        LOGGER.debug("DAO insert Doctor {}", doctor);
        try {
            userMapper.insert(doctor.getUser());
            if (doctorMapper.insert(doctor) == 0) {
                throw new ServerException(ServerError.OTHER_ERROR);
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Doctor {} {}", doctor, ex);
            throw new ServerException(ServerError.LOGIN_ALREADY_EXISTS);
        }
        return doctor;
    }

    @Override
    public Doctor getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get Doctor by token {}", token);
        try {
            return doctorMapper.getByToken(token);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor by token {} {}", token, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public Doctor getByUserId(int id) throws ServerException {
        LOGGER.debug("DAO get Doctor by User id {}", id);
        try {
            return doctorMapper.getByUserId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor by User id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public Doctor getByDoctorId(int id) throws ServerException {
        LOGGER.debug("DAO get Doctor by id {}", id);
        try {
            return doctorMapper.getByDoctorId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor by id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }


    @Override
    public List<Doctor> getBySpeciality(String speciality) throws ServerException {
        LOGGER.debug("DAO get All Doctor with speciality {}", speciality);
        try {
            return doctorMapper.getBySpeciality(speciality);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get All Doctor with speciality {} {}", speciality, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<Statistic> getTicketCount(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO get Doctors count");
        try {
            return doctorMapper.getTicketsCount(id, dateStart, dateEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctors count {} ", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

}
