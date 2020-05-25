package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.mappers.CommissionMapper;
import net.thumbtack.school.hospital.database.mappers.DayScheduleMapper;
import net.thumbtack.school.hospital.database.mappers.TicketScheduleMapper;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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
                ret += ticketScheduleMapper.insertScheduleTicketList(s.getTicketSchedule(), s.getId());
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Schedule {} {}", daySchedule, ex);
            throw new ServerException(ServerError.INSERT_SCHEDULE_FAIL);
        }
        return ret;
    }

    @Override
    public boolean addTicket(TicketSchedule schedule) throws ServerException {
        LOGGER.debug("DAO insert Ticket {}", schedule);
        int ret;
        try {
            ret = ticketScheduleMapper.insertTicket(schedule);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Ticket {} {}", schedule, ex);
            throw new ServerException(ServerError.INSERT_TICKET_FAIL);
        }
        return ret == 1;
    }

    @Override
    public boolean addCommissionTicket(Commission commission, List<Integer> ticketScheduleList) throws ServerException {
        LOGGER.debug("DAO insert Commission Ticket {}", commission);
        int ret;
        try {
            ret = commissionMapper.insertCommissionTicket(ticketScheduleList, commission);
            if (ret != ticketScheduleList.size()) {
                throw new ServerException(ServerError.INSERT_TICKET_FAIL);
            }
            ret = commissionMapper.insertCommission(commission);
            commissionMapper.insertCommissionDoctorsId(new ArrayList<Doctor>(commission.getDoctorSet()), commission.getId());
        } catch (DataAccessException ex) {
            LOGGER.info("Can't insert Commission Ticket {} {}", commission, ex);
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
    public List<Integer> getCountFreeSchedule(List<Integer> doctorIds, LocalDate date, LocalTime timeStart, LocalTime timeEnd) throws ServerException {
        LOGGER.debug("Get count free Ticket from schedule with doctors id {}, date {}, time {} - {}", doctorIds, date, timeStart, timeEnd);
        try {
            return ticketScheduleMapper.getTicketsIdByDoctorsId(doctorIds, date, timeStart, timeEnd);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get count free Ticket from schedule with doctors id {}, date {}, time {} - {}. {}", doctorIds, date, timeStart, timeEnd, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public List<Commission> getCommission(int patientId) throws ServerException {
        LOGGER.debug("DAO get schedule with ticket list by patient ID {}.", patientId);
        try {
            return commissionMapper.getByPatientId(patientId);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get schedule with ticket list by patient ID {}, {}", patientId, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

    @Override
    public void cancelTicket(String ticket, int patientId) throws ServerException {
        LOGGER.debug("Cancel Ticket {} from schedule.", ticket);
        try {
            if (ticketScheduleMapper.cancelTicket(ticket, patientId) != 1){
                throw new ServerException(ServerError.CANCEL_TICKET_FAIL);
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't delete ticket {} from schedule. {}", ticket, ex);
            throw new ServerException(ServerError.CANCEL_TICKET_FAIL);
        }
    }

    @Override
    public void cancelCommission(String ticket, int patientId) throws ServerException {
        LOGGER.debug("Cancel Ticket {} from schedule.", ticket);
        try {
            if (commissionMapper.deleteCommission(ticket, patientId) == 0) {
                throw new ServerException(ServerError.CANCEL_TICKET_FAIL);
            }
            if (commissionMapper.cancelCommissionSchedule(ticket) == 0){
                throw new ServerException(ServerError.CANCEL_TICKET_FAIL);
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't delete ticket {} from schedule. {}", ticket, ex);
            throw new ServerException(ServerError.CANCEL_TICKET_FAIL);
        }
    }

    @Override
    public void updateSchedule(Doctor doctor) throws ServerException {
        LocalDate dateStart = doctor.getDayScheduleList().get(0).getDate();
        LocalDate dateEnd = doctor.getDayScheduleList().get(doctor.getDayScheduleList().size()-1).getDate();
        LOGGER.debug("Update Doctor schedule By ID {} from date {} to date {}.", doctor.getId(), dateStart, dateEnd);
        try {
            dayScheduleMapper.deleteFreeScheduleByDoctorIdByDate(doctor.getId(), dateStart, dateEnd.plusDays(1));
            if (dayScheduleMapper.getDaySchedule(doctor.getId(), null, dateStart, dateEnd.plusDays(1)).size() != 0) {
                throw new ServerException(ServerError.NOT_AVAILABLE_SCHEDULE);
            }
            dayScheduleMapper.insert(doctor.getDayScheduleList());
            for (DaySchedule s : doctor.getDayScheduleList()) {
                ticketScheduleMapper.insertScheduleTicketList(s.getTicketSchedule(), s.getId());
            }
        } catch (DataAccessException ex) {
            LOGGER.info("Can't update doctor schedule by Id {} from date {} to date {}. {}", doctor.getId(), dateStart, dateEnd, ex);
            throw new ServerException(ServerError.INSERT_SCHEDULE_FAIL);
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

}
