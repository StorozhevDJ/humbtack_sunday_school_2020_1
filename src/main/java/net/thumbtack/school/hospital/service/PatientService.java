package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.PatientDao;
import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.AddTicketDtoRequest;
import net.thumbtack.school.hospital.dto.request.EditPatientDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddTicketDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.dto.response.getticket.GetTicketListDtoResponse;
import net.thumbtack.school.hospital.mapper.PatientMapper;
import net.thumbtack.school.hospital.mapper.ScheduleMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(rollbackFor = ServerException.class)
public class PatientService {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private final PatientDao patientDao;
    private final UserDao userDao;
    private final DoctorDao doctorDao;
    private final ScheduleDao scheduleDao;

    @Autowired
    public PatientService(PatientDao patientDao, UserDao userDao, DoctorDao doctorDao, ScheduleDao scheduleDao) {
        this.patientDao = patientDao;
        this.userDao = userDao;
        this.doctorDao = doctorDao;
        this.scheduleDao = scheduleDao;
    }

    /**
     * Register new patient
     *
     * @param cookie
     * @param registerPatientDtoRequest
     * @return LoginDtoResponse
     * @throws ServerException
     */
    public LoginDtoResponse registerPatient(String cookie, RegisterPatientDtoRequest registerPatientDtoRequest) throws ServerException {
        Patient patient = PatientMapper.convertToEntity(registerPatientDtoRequest);
        patient = patientDao.insert(patient);
        patient.getUser().setSession(new Session(patient.getUser().getId(), cookie));
        userDao.logIn(patient.getUser());
        return PatientMapper.convertToDto(patient);
    }

    /**
     * Get Patient info by ID
     *
     * @param cookie
     * @param patientId
     * @return LoginDtoResponse
     * @throws ServerException
     */
    public LoginDtoResponse infoPatient(String cookie, int patientId) throws ServerException {
        User user = userDao.getByToken(cookie);
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        if ((user.getUserType() != UserType.ADMINISTRATOR) && (user.getUserType() != UserType.DOCTOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        Patient patient = patientDao.getByPatientId(patientId);
        if (patient == null) {
            throw new ServerException(ServerError.PATIENT_NOT_FOUND);
        }
        return PatientMapper.convertToDto(patient);
    }

    /**
     * Change patient account information
     *
     * @param cookie
     * @param editPatientDtoRequest
     * @return LoginDtoResponse
     * @throws ServerException
     */
    public LoginDtoResponse editPatient(String cookie, EditPatientDtoRequest editPatientDtoRequest) throws ServerException {
        Patient patient = patientDao.getByToken(cookie);
        if (patient == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        try {
            if (!getMD5Hash(editPatientDtoRequest.getOldPassword()).equals(patient.getUser().getPassword())) {
                throw new ServerException(ServerError.LOGIN_OR_PASSWORD_INVALID);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new ServerException(ServerError.OTHER_ERROR);
        }
        patient.getUser().setFirstName(editPatientDtoRequest.getFirstName());
        patient.getUser().setLastName(editPatientDtoRequest.getLastName());
        patient.getUser().setPatronymic(editPatientDtoRequest.getPatronymic());
        patient.getUser().setPassword(editPatientDtoRequest.getNewPassword());
        patient.setAddress(editPatientDtoRequest.getAddress());
        patient.setPhone(editPatientDtoRequest.getPhone());
        patient.setEmail(editPatientDtoRequest.getEmail());
        patientDao.update(patient);
        return PatientMapper.convertToDto(patient);
    }

    private static String getMD5Hash(String s) throws NoSuchAlgorithmException {
        StringBuilder result = new StringBuilder(s);
        if (s != null) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            result = new StringBuilder(hash.toString(16));
            while (result.length() < 32) {
                result.insert(0, "0");
            }
        }
        return result.toString();
    }

    /**
     * Add new ticket
     *
     * @param cookie
     * @param addTicketDtoRequest
     * @return AddTicketDtoResponse
     * @throws ServerException
     */
    public AddTicketDtoResponse addTicket(String cookie, AddTicketDtoRequest addTicketDtoRequest) throws ServerException {
        // Check user
        Patient patient = patientDao.getByToken(cookie);
        if (patient == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        LocalDate date = LocalDate.parse(addTicketDtoRequest.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        // Get schedule by doctor ID or speciality for required date
        List<DaySchedule> dayScheduleList = scheduleDao.getDaySchedule(
                (addTicketDtoRequest.getDoctorId() != null) ? Integer.parseInt(addTicketDtoRequest.getDoctorId()) : 0,
                addTicketDtoRequest.getSpeciality(),
                date,
                date.plusDays(1)
        );
        if ((dayScheduleList == null) || (dayScheduleList.size() == 0)) {
            throw new ServerException(ServerError.NOT_AVAILABLE_SCHEDULE);
        }
        // Get free ticket list for this day schedule and time
        LocalTime time = LocalTime.parse(addTicketDtoRequest.getTime(), DateTimeFormatter.ofPattern(TIME_FORMAT));
        List<TicketSchedule> ticketScheduleList = scheduleDao.getTicketSchedule(dayScheduleList.get(0).getId(), ScheduleType.FREE, time, null);

        // Copy date for insert ticket and response
        DaySchedule daySchedule = dayScheduleList.get(0);
        TicketSchedule ticketSchedule = ticketScheduleList.get(0);
        daySchedule.setTicketSchedule(ticketScheduleList);
        daySchedule.setDoctor(daySchedule.getDoctor());
        daySchedule.setDate(daySchedule.getDate());
        ticketSchedule.setTicket("D" + addTicketDtoRequest.getDoctorId() + date.format(DateTimeFormatter.ofPattern("ddMMyyyy")) + time.format(DateTimeFormatter.ofPattern("HHmm")));
        ticketSchedule.setPatient(patient);
        ticketSchedule.setScheduleType(ScheduleType.RECEPTION);

        scheduleDao.addTicket(ticketSchedule);

        return ScheduleMapper.convertToDto(daySchedule);
    }

    /**
     * Cancel an existing doctor ticket
     *
     * @param cookie
     * @param ticket
     * @return EmptyDtoResponse
     * @throws ServerException
     */
    public EmptyDtoResponse cancelTicket(String cookie, String ticket) throws ServerException {
        // Get user
        Patient patient = patientDao.getByToken(cookie);
        if (patient == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        scheduleDao.cancelTicket(ticket, patient.getId());
        return new EmptyDtoResponse();
    }

    /**
     * Get Patient tickets list
     *
     * @param cookie
     * @return
     * @throws ServerException
     */
    public GetTicketListDtoResponse getTicketsList(String cookie) throws ServerException {
        // Get patient
        Patient patient = patientDao.getByToken(cookie);
        if (patient == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        //Get tickets list for doctor reception
        List<DaySchedule> ticketScheduleList = scheduleDao.getTicketsListByPatientId(patient.getId());
        List<Commission> commissionList = scheduleDao.getCommission(patient.getId());

        return ScheduleMapper.convertToDto(ticketScheduleList, commissionList);
    }

    /**
     * Cancel commission
     *
     * @param cookie
     * @param ticket
     * @return
     * @throws ServerException
     */
    public EmptyDtoResponse cancelCommission(String cookie, String ticket) throws ServerException {
        Patient patient = patientDao.getByToken(cookie);
        if (patient == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        scheduleDao.cancelCommission(ticket, patient.getId());
        return new EmptyDtoResponse();
    }

}
