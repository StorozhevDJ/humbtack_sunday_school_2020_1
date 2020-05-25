package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.mapper.DoctorMapper;
import net.thumbtack.school.hospital.mapper.ScheduleMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DoctorService {

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private final DoctorDao doctorDao;
    private final UserDao userDao;
    private final ScheduleDao scheduleDao;

    @Autowired
    public DoctorService(DoctorDao doctorDao, UserDao userDao, ScheduleDao scheduleDao) {
        this.doctorDao = doctorDao;
        this.userDao = userDao;
        this.scheduleDao = scheduleDao;
    }

    /**
     * 3.8. Get doctor information by ID
     * QueryParam schedule = “yes | no”
     * QueryParam startDate = “date”
     * QueryParam endDate = “date”
     */
    public EditScheduleDtoResponse doctorInfo(String cookie, int docId, String schedule, String startDate, String endDate) throws ServerException {
        User user = userDao.getByToken(cookie);
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        Doctor doctor = doctorDao.getByDoctorId(docId);
        if (doctor == null) {
            throw new ServerException(ServerError.USER_ID_INVALID);
        }
        List<DaySchedule> dayScheduleList = null;
        if (("yes".equals(schedule))) {
            LocalDate dateStart = (startDate == null) ? LocalDate.now() : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateEnd = (endDate == null) ? LocalDate.now().plusMonths(2) : LocalDate.parse(endDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            dayScheduleList = scheduleDao.getByDoctorId(docId, dateStart, dateEnd);

            // Copy info about patient
            if (user.getUserType() == UserType.PATIENT) {
                for (DaySchedule daySchedule : dayScheduleList) {
                    for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                        if (ticketSchedule.getPatient().getUser().getId() != user.getId()) {
                            ticketSchedule.setPatient(null);
                        }
                    }
                }
            }
        }
        return DoctorMapper.convertToDto(doctor, dayScheduleList);
    }

    /**
     * 3.9. Get all doctors information by speciality
     * cookie JAVASESSIONID
     * QueryParam schedule = “yes | no”
     * QueryParam speciality = “специальность”
     * QueryParam startDate = “дата”
     * QueryParam endDate = “дата”
     */
    public List<EditScheduleDtoResponse> doctorsInfo(String cookie, String speciality, String schedule, String startDate, String endDate) throws ServerException {
        User user = userDao.getByToken(cookie);
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        List<DaySchedule> dayScheduleList = null;
        if (("yes".equals(schedule))) {
            LocalDate dateStart = (startDate == null) ? LocalDate.now() : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateEnd = (endDate == null) ? LocalDate.now().plusMonths(2) : LocalDate.parse(endDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            dayScheduleList = scheduleDao.getByDoctorSpeciality(speciality, dateStart, dateEnd);
        }
        if (dayScheduleList == null) {
            throw new ServerException(ServerError.DOCTOR_NOT_FOUND);
        }
        if (user.getUserType() == UserType.PATIENT) {
            return DoctorMapper.convertToDto(dayScheduleList, user);
        }
        return DoctorMapper.convertToDto(dayScheduleList, null);
    }

    /**
     * Add commission ticket  into DB
     *
     * @param cookie
     * @param addCommissionDtoRequest
     * @return AddCommissionDtoResponse
     * @throws ServerException
     */
    public AddCommissionDtoResponse addCommission(String cookie, AddCommissionDtoRequest addCommissionDtoRequest) throws ServerException {
        Doctor doctor = doctorDao.getByToken(cookie);
        if (doctor == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        // Prepare data to models and create commission tickets string
        LocalDate date = LocalDate.parse(addCommissionDtoRequest.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalTime timeStart = LocalTime.parse(addCommissionDtoRequest.getTime(), DateTimeFormatter.ofPattern(TIME_FORMAT));
        LocalTime timeEnd = timeStart.plusMinutes(addCommissionDtoRequest.getDuration());
        StringBuilder sb = new StringBuilder("CD");
        for (Integer id : addCommissionDtoRequest.getDoctorIds()) {
            sb.append(id);
            sb.append('D');
        }
        sb.append(date.format(DateTimeFormatter.ofPattern("ddMMyyyy")));
        sb.append(timeStart.format(DateTimeFormatter.ofPattern("HHmm")));
        Set<Integer> idSet = new HashSet<>(Arrays.asList(addCommissionDtoRequest.getDoctorIds()));
        idSet.add(doctor.getId());
        Set<Doctor> doctorSet = new HashSet<>();
        for (Integer id : idSet) {
            Doctor doctorId = new Doctor();
            doctorId.setId(id);
            doctorSet.add(doctorId);
        }
        Patient patient = new Patient();
        patient.setId(addCommissionDtoRequest.getPatientId());
        Commission commission = new Commission(sb.toString(), timeStart, timeEnd, date, addCommissionDtoRequest.getRoom(), addCommissionDtoRequest.getPatientId(), doctorSet);
        //Insert date to DB
        List<Integer> ticketList = scheduleDao.getCountFreeSchedule(Arrays.asList(addCommissionDtoRequest.getDoctorIds()), date, timeStart, timeEnd);
        scheduleDao.addCommissionTicket(commission, ticketList);

        return ScheduleMapper.convertToDto(addCommissionDtoRequest, commission);
    }

}
