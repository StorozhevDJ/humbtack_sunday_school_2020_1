package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.mappers.CommissionMapper;
import net.thumbtack.school.hospital.database.mappers.DayScheduleMapper;
import net.thumbtack.school.hospital.database.mappers.TicketScheduleMapper;
import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.ScheduleType;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ScheduleDaoImpl implements ScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    private DayScheduleMapper dayScheduleMapper;
    private TicketScheduleMapper ticketScheduleMapper;
    private CommissionMapper commissionMapper;

    public ScheduleDaoImpl() {
    }

    @Autowired
    public ScheduleDaoImpl(DayScheduleMapper dayScheduleMapper, TicketScheduleMapper ticketScheduleMapper, CommissionMapper commissionMapper) {
        this.dayScheduleMapper = dayScheduleMapper;
        this.ticketScheduleMapper = ticketScheduleMapper;
        this.commissionMapper = commissionMapper;
    }

    @Override
    public int createSchedule(List<DaySchedule> daySchedule) throws ServerException {
        LOGGER.debug("DAO insert Schedule {}", daySchedule);
        int ret = 0;
        try {
            dayScheduleMapper.insert(daySchedule);
            for (DaySchedule s : daySchedule) {
                ret += ticketScheduleMapper.insertTicketList(s.getTicketSchedule(), s.getId());
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Schedule {} {}", daySchedule, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
        return ret;
    }


    @Override
    public boolean addTicket(TicketSchedule schedule) throws ServerException {
        LOGGER.debug("DAO insert Ticket {}", schedule);
        int ret = 0;
        try {
            ret = ticketScheduleMapper.insertTicket(schedule);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Ticket {} {}", schedule, ex);
            throw new ServerException(ServerError.INSERT_TICKET_FAIL);
        }
        return ret == 1;
    }

    @Override
    public List<DaySchedule> getByDoctorId(int id, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO get Doctor Schedule by id {}", id);
        try {
            return dayScheduleMapper.getDaySchedule(id, null, dateStart, dateEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor Schedule by id {} {}", id, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getByDoctorSpeciality(String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO get Doctor Schedule by speciality {}", speciality);
        try {
            return dayScheduleMapper.getDaySchedule(0, speciality, dateStart, dateEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor Schedule by speciality {} {}", speciality, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getAllSchedule() throws ServerException {
        LOGGER.debug("DAO get All Doctor Schedule");
        try {
            return dayScheduleMapper.getDaySchedule(0, null, null, null);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get All Doctor Schedule {}", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<TicketSchedule> getTicketScheduleById(int scheduleId) throws ServerException {
        LOGGER.debug("DAO get Day Schedule by id {}", scheduleId);
        try {
            return ticketScheduleMapper.getTicketSchedule(scheduleId, null, null, null);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Day Schedule by id {} {}", scheduleId, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<TicketSchedule> getTicketSchedule(int scheduleId, ScheduleType type, LocalTime timeStart, LocalTime timeEnd) throws ServerException {
        LOGGER.debug("DAO get Day Schedule by parameters. Schedule ID {}, Type {}, Time Start {}, End {}.", scheduleId, type, timeStart, timeEnd);
        try {
            return ticketScheduleMapper.getTicketSchedule(scheduleId, type, timeStart, timeEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Day Schedule by id {}, type {}, timeStart {}, timeEnd {}, {}", scheduleId, type, timeStart, timeEnd, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getTicketsListByPatientId(int patientId) throws ServerException {
        LOGGER.debug("DAO get schedule with ticket list by patient ID {}.", patientId);
        try {
            return dayScheduleMapper.getTicketListByPatientId(patientId);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get schedule with ticket list by patient ID {}, {}", patientId, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> getDaySchedule(int doctorId, String speciality, LocalDate dateStart, LocalDate dateEnd) throws ServerException {
        LOGGER.debug("DAO get Doctor Schedule by speciality {}", speciality);
        try {
            return dayScheduleMapper.getDaySchedule(doctorId, speciality, dateStart, dateEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Doctor Schedule by speciality {} {}", speciality, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<DaySchedule> checkFreeScheduleToCommission(List<Integer> doctorIds, LocalDate date, LocalTime timeStart, LocalTime timeEnd) throws ServerException {
        LOGGER.debug("Check free Ticket {} from schedule with doctors id {}, date {}, time {} - {}", doctorIds, date, timeStart, timeEnd);
        try {
            return dayScheduleMapper.getTicketListByDoctorsId(doctorIds, date, timeStart, timeEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't Check free Ticket {} from schedule with doctors id {}, date {}, time {} - {}. {}", doctorIds, date, timeStart, timeEnd, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public boolean cancelTicket(String ticket) throws ServerException {
        LOGGER.debug("Cancel Ticket {} from schedule.", ticket);
        try {
            return ticketScheduleMapper.cancelTicket(ticket) == 1 ? true : false;
        } catch (DataAccessException ex) {
            LOGGER.info("Can't delete ticket {} from schedule. {}", ticket, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public void deleteSchedule(int doctorId, LocalDate date) throws ServerException {
        LOGGER.debug("Delete Doctor schedule By ID {} from date {}", doctorId, date);
        try {
            dayScheduleMapper.deleteScheduleByDoctorIdFromDate(doctorId, date);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't delete doctor schedule by Id {} from date {}. {}", doctorId, date, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }


    @Override
    public boolean addCommissionTicket(Commission schedule, TicketSchedule ticketSchedule) throws ServerException {
        LOGGER.debug("DAO insert Commission Ticket {}", schedule);
        int ret = 0;
        try {
            ret = commissionMapper.insertCommission(schedule);
            if (ret == 1) {
                ret = ticketScheduleMapper.insertCommissionTicket(ticketSchedule);
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Commission Ticket {} {}", schedule, ex);
            throw new ServerException(ServerError.INSERT_TICKET_FAIL);
        }
        return ret >= 1;
    }


}
