package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.AddCommissionDtoRequest;
import net.thumbtack.school.hospital.dto.response.AddCommissionDtoResponse;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
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
import java.util.*;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class DoctorService {

    private static final String DATE_FORMAT = "ddMMyyyy";
    private static final String TIME_FORMAT = "HHmm";

    private DoctorDao doctorDao;
    private UserDao userDao;
    private ScheduleDao scheduleDao;

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
        User user = userDao.getByToken(new Session(cookie));
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        Doctor doctor = doctorDao.getByDoctorId(docId);
        if (doctor == null) {
            throw new ServerException(ServerError.USER_ID_INVALID);
        }
        List<DaySchedule> dayScheduleList = null;
        if ((schedule != null) && ("yes".equals(schedule))) {
            LocalDate dateStart = (startDate == null) ? LocalDate.now() : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateEnd = (endDate == null) ? LocalDate.now().plusMonths(2) : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            dayScheduleList = scheduleDao.getByDoctorId(docId, dateStart, dateEnd);
        }
        // Delete info about other patients
        if (user.getType() == UserType.PATIENT) {
            for (DaySchedule daySchedule : dayScheduleList) {
                for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                    if (ticketSchedule.getPatient().getUser().getId() != user.getId()) {
                        ticketSchedule.setPatient(null);
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
    	// что-то я идею не пойму
    	// есть cookie. Вы ее заворачиваете в Session, где есть еще user_id, который при этом получает значение 0
    	// а после этого берете userDao.getByToken, передавая туда эту Session, чтобы там потом взять из нее token и по нему получить User
    	// может, проще в getByToken просто cookie передать ?
        User user = userDao.getByToken(new Session(cookie));
        if (user == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        List<DaySchedule> dayScheduleList = null;
        if ((schedule != null) && ("yes".equals(schedule))) {
            LocalDate dateStart = (startDate == null) ? LocalDate.now() : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            LocalDate dateEnd = (endDate == null) ? LocalDate.now().plusMonths(2) : LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
            dayScheduleList = scheduleDao.getByDoctorSpeciality(speciality, dateStart, dateEnd);
        }
        // Delete info about other patients
        // REVU вместо того, чтобы удалять, лучше не добавлять, если не нужно
        if (user.getType() == UserType.PATIENT) {
            for (DaySchedule daySchedule : dayScheduleList) {
                for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                    if (ticketSchedule.getPatient().getUser().getId() != user.getId()) {
                        ticketSchedule.setPatient(null);
                    }
                }
            }
        }
        return DoctorMapper.convertToDto(dayScheduleList);
    }

    // ToDo
    public AddCommissionDtoResponse addCommission(String cookie, AddCommissionDtoRequest addCommissionDtoRequest) throws ServerException {
        Doctor doctor = doctorDao.getByToken(new Session(cookie));
        if (doctor == null) {
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
        // Check doctors schedule to free by doctors IDs, date time start and time end
        // REVU проверили,и что ? А если между scheduleDao.checkFreeScheduleToCommission и scheduleDao.addCommissionTicket
        // другой пациент займет это время ?
        // нельзя так
        // надо найти все TicketSchedule всех докторов комиссии, которые нужно захватить для комиссии
        // и поставить им всем ScheduleType.COMISSION, если они ScheduleType.FREE
        // и определить, сколько поставилось (mapper.update вернет это значение)
        // если поставилось столько, сколько ставили - значит, все были FREE и можно продолжать
        // а иначе rollback
        LocalDate date = LocalDate.parse(addCommissionDtoRequest.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalTime timeStart = LocalTime.parse(addCommissionDtoRequest.getTime(), DateTimeFormatter.ofPattern(TIME_FORMAT));
        LocalTime timeEnd = timeStart.plusMinutes(addCommissionDtoRequest.getDuration());
        List<DaySchedule> dayScheduleList = scheduleDao.checkFreeScheduleToCommission(Arrays.asList(addCommissionDtoRequest.getDoctorIds()), date, timeStart, timeEnd);
        if (dayScheduleList.size() != 0) {
            throw new ServerException(ServerError.NOT_AVAILABLE_SCHEDULE);
        }
        // Create models and add commission ticket to schedule
        StringBuilder sb = new StringBuilder("CD");
        for (Integer id : addCommissionDtoRequest.getDoctorIds()) {
            sb.append(id);
            sb.append('D');
        }
        sb.append(addCommissionDtoRequest.getDate());
        sb.append(addCommissionDtoRequest.getTime());
        // REVU сделайте нужный конструктор в Commission и не вызывайте сеттеры без необходимости 
        Commission commission = new Commission();
        commission.setTicket(sb.toString());
        Set<Integer> idSet = new HashSet<>(Arrays.asList(addCommissionDtoRequest.getDoctorIds()));
        idSet.add(doctor.getId());
        commission.setDoctorIdSet(idSet);
        commission.setTimeStart(timeStart);
        commission.setTimeEnd(timeEnd);
        commission.setDate(date);
        commission.setRoom(addCommissionDtoRequest.getRoom());
        commission.setPatientId(addCommissionDtoRequest.getPatientId());

        // REVU то же
        TicketSchedule ticketSchedule = new TicketSchedule();
        // REVU это что за бомж ?
        Patient patient = new Patient(new User(), null, null, null);
        patient.setId(addCommissionDtoRequest.getPatientId());
        ticketSchedule.setPatient(patient);
        ticketSchedule.setTicket(sb.toString());
        ticketSchedule.setTimeStart(timeStart);
        ticketSchedule.setTimeEnd(timeEnd);
        ticketSchedule.setScheduleType(ScheduleType.COMMISSION);
        scheduleDao.addCommissionTicket(commission, ticketSchedule);

        return ScheduleMapper.convertToDto(addCommissionDtoRequest, commission);
    }

}
