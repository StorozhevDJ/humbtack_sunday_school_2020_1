package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.DayScheduleDao;
import net.thumbtack.school.hospital.database.mappers.DayScheduleMapper;
import net.thumbtack.school.hospital.database.mappers.TicketScheduleMapper;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DayScheduleDaoImpl implements DayScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    private DayScheduleMapper dayScheduleMapper;
    private TicketScheduleMapper ticketScheduleMapper;

    public DayScheduleDaoImpl() {
    }

    @Autowired
    public DayScheduleDaoImpl(DayScheduleMapper dayScheduleMapper, TicketScheduleMapper ticketScheduleMapper) {
        this.dayScheduleMapper = dayScheduleMapper;
        this.ticketScheduleMapper = ticketScheduleMapper;
    }

    @Override
    public int createSchedule(List<DaySchedule> daySchedule) throws ServerException {
        LOGGER.debug("DAO insert Schedule {}", daySchedule);
        int ret = 0;
        try {
            dayScheduleMapper.insert(daySchedule);
            for (DaySchedule s : daySchedule) {
                ret += ticketScheduleMapper.insertTicket(s.getTicketSchedule(), s.getId());
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Schedule {} {}", daySchedule, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
        return ret;
    }

    @Override
    public List<DaySchedule> getByDoctorId(int id) throws ServerException {
        LOGGER.debug("DAO get Doctor Schedule by id {}", id);
        try {
            return dayScheduleMapper.getByDoctorId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor Schedule by id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getByDoctorSpeciality(String speciality) throws ServerException {
        LOGGER.debug("DAO get Doctor Schedule by speciality {}", speciality);
        try {
            return dayScheduleMapper.getBySpeciality(speciality);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor Schedule by speciality {} {}", speciality, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getAllSchedule() throws ServerException {
        LOGGER.debug("DAO get All Doctor Schedule");
        try {
            return dayScheduleMapper.getAll();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get All Doctor Schedule {}", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public boolean addTicket(TicketSchedule schedule) throws ServerException {
        LOGGER.debug("DAO insert Ticket {}", schedule);
        int ret = 0;
            try {
                ret = ticketScheduleMapper.insertTicketByDoctorId(schedule);
            } catch (DataAccessException ex) {
                LOGGER.info("Can't insert Ticket {} {}", schedule, ex);
                throw new ServerException(ServerError.OTHER_ERROR);
            }
        return ret == 1;
    }


}
