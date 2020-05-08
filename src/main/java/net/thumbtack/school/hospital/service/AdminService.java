package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.mapper.AdminMapper;
import net.thumbtack.school.hospital.mapper.DoctorMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(rollbackFor = ServerException.class)
public class AdminService {

    private AdminDao adminDao;
    private UserDao userDao;
    private DoctorDao doctorDao;
    private ScheduleDao scheduleDao;

    @Autowired
    public AdminService(AdminDao adminDao, UserDao userDao, DoctorDao doctorDao, ScheduleDao scheduleDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.doctorDao = doctorDao;
        this.scheduleDao = scheduleDao;
    }


    /**
     * Register new administrator
     *
     * @param registerAdminDtoRequest
     * @param cookie
     * @return
     * @throws ServerException
     */
    public LoginDtoResponse registerAdmin(RegisterAdminDtoRequest registerAdminDtoRequest, String cookie) throws ServerException {
        Admin admin = AdminMapper.convertToEntity(registerAdminDtoRequest);
        admin = adminDao.insert(admin);
        admin.getUser().setSession(new Session(admin.getUser().getId(), cookie));
        userDao.logIn(admin.getUser());
        return AdminMapper.convertToDto(admin);
    }


    /**
     * Update Administrator info (only current administrator)
     *
     * @param editAdminDtoRequest
     * @param cookie
     * @return
     * @throws ServerException
     */
    public LoginDtoResponse editAdmin(EditAdminDtoRequest editAdminDtoRequest, String cookie) throws ServerException {
        // Check user type
        User user = userDao.getByToken(new Session(cookie));
        if ((user != null) && (user.getType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // Update Admin position, if required
        Admin admin = adminDao.getByToken(new Session(cookie));
        if (admin.getPosition() != editAdminDtoRequest.getPosition()) {
            admin.setPosition(editAdminDtoRequest.getPosition());
            adminDao.update(admin);
        }
        // Update user info
        userDao.update(AdminMapper.convertToEntity(editAdminDtoRequest, user.getLogin()).getUser());
        return AdminMapper.convertToDto(admin);
    }


    /**
     * Register new doctor and create schedule
     *
     * @param registerDtoRequest
     * @param cookie
     * @return
     * @throws ServerException
     */
    public EditScheduleDtoResponse registerDoctor(RegisterDoctorDtoRequest registerDtoRequest, String cookie) throws ServerException {
        // Check user and permission
        User user = userDao.getByToken(new Session(cookie));
        if ((user != null) && (user.getType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // Insert new doctor to DB
        Doctor doctor = DoctorMapper.convertToEntity(registerDtoRequest);
        doctor = doctorDao.insert(doctor);

        LocalDate dateStart = LocalDate.parse(registerDtoRequest.getDateStart(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalDate dateEnd = LocalDate.parse(registerDtoRequest.getDateEnd(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        if (dateEnd.isAfter(dateStart.plusMonths(2))) {
            dateEnd = dateStart;
        }

        List<DaySchedule> dayScheduleList;
        // Create from WeekSchedule
        if (registerDtoRequest.getWeekSchedule() != null) {
            dayScheduleList = createWeekSchedule(registerDtoRequest.getWeekSchedule(), dateStart, dateEnd, registerDtoRequest.getDuration(), doctor);
        }
        // Create from WeekDaysSchedule
        else if (registerDtoRequest.getWeekDaysSchedules() != null) {
            // For each day of the week
            dayScheduleList = createWeekDaysSchedule(registerDtoRequest.getWeekDaysSchedules(), dateStart, dateEnd, registerDtoRequest.getDuration(), doctor);
        } else {
            ServerError er = ServerError.BAD_REQUEST_S;
            er.setMessage(String.format(er.getMessage(), " Empty schedule"));
            throw new ServerException(er);
        }
        scheduleDao.createSchedule(dayScheduleList);

        return DoctorMapper.convertToDto(doctor, dayScheduleList);
    }


    /**
     * Edit doctor schedule (add new)
     *
     * @param editDto
     * @param doctorId
     * @param cookie
     * @return
     * @throws ServerException
     */
    public EditScheduleDtoResponse editScheduleDoctor(EditScheduleDtoRequest editDto, int doctorId, String cookie) throws ServerException {

        EditScheduleDtoResponse dto = null;

        return dto;
    }


    /**
     * Delete doctor schedule from request date
     *
     * @param deleteDoctorDtoRequest
     * @param doctorId
     * @param cookie
     * @return
     * @throws ServerException
     */
    public EmptyDtoResponse deleteDoctor(DeleteDoctorDtoRequest deleteDoctorDtoRequest, int doctorId, String cookie) throws ServerException {
        User user = userDao.getByToken(new Session(cookie));
        if ((user != null) && (user.getType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // ToDo add send sms/email
        LocalDate date = LocalDate.parse(deleteDoctorDtoRequest.getDate(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        List<DaySchedule> dayScheduleList = scheduleDao.getByDoctorId(doctorId, date, date.plusMonths(2));

        scheduleDao.deleteSchedule(doctorId, date);
        return new EmptyDtoResponse();
    }


    /**
     * Create Day Schedule list from RegisterDoctorDtoRequest DTO with WeekSchedule
     *
     * @param weekSchedule DTO Request
     * @param doctor
     * @return
     * @throws ServerException
     */
    private List<DaySchedule> createWeekSchedule(WeekScheduleDtoRequest weekSchedule, LocalDate dateStart, LocalDate dateEnd, int duration, Doctor doctor) throws ServerException {
        EnumSet<DayOfWeek> dayOfWeeksSet = EnumSet.noneOf(DayOfWeek.class);
        List<DaySchedule> dayScheduleList = new ArrayList<>();
        LocalTime timeStart, timeEnd;
        // Day of weeks set
        if (weekSchedule.getWeekDays() != null) {
            for (String weekDaysString : weekSchedule.getWeekDays()) {
                dayOfWeeksSet.add(parseDayOfWeek(weekDaysString));
            }
        } else {    //Set default Weekdays work
            dayOfWeeksSet = EnumSet.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        }
        // For each day of the week
        for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
            if (dayOfWeeksSet.contains(date.getDayOfWeek())) {
                timeStart = LocalTime.parse(weekSchedule.getTimeStart(), DateTimeFormatter.ofPattern("HHmm"));
                timeEnd = LocalTime.parse(weekSchedule.getTimeEnd(), DateTimeFormatter.ofPattern("HHmm"));
                // For each hour in day
                List<TicketSchedule> ticketScheduleList = new ArrayList<>();
                for (LocalTime t = timeStart; t.isBefore(timeEnd); t = t.plusMinutes(duration)) {
                	// REVU сделайте конструктор с параметрами
                    TicketSchedule ticketSchedule = new TicketSchedule();
                    ticketSchedule.setTimeStart(t);
                    ticketSchedule.setTimeEnd(t.plusMinutes(duration));
                    ticketSchedule.setScheduleType(ScheduleType.FREE);
                    ticketScheduleList.add(ticketSchedule);
                }
                // Create day schedule list
                DaySchedule daySchedule = new DaySchedule(doctor, date);
                daySchedule.setTicketSchedule(ticketScheduleList);
                dayScheduleList.add(daySchedule);
            }
        }
        return dayScheduleList;
    }


    /**
     * Create Day Schedule list from createWeekDaysSchedule DTO with WeekSchedule
     *
     * @throws ServerException
     */
    private List<DaySchedule> createWeekDaysSchedule(WeekDaysScheduleDtoRequest weekDaysSchedule, LocalDate dateStart, LocalDate dateEnd, int duration, Doctor doctor) throws ServerException {
        List<DaySchedule> dayScheduleList = new ArrayList<>();
        LocalTime timeStart, timeEnd;
        // Set EnumSet with map time start & time end from DTO
        EnumSet<DayOfWeek> dayOfWeeksSet = EnumSet.noneOf(DayOfWeek.class);
        Map<DayOfWeek, LocalTime> timeStartMap = new HashMap<>();
        Map<DayOfWeek, LocalTime> timeEndMap = new HashMap<>();
        for (DayScheduleDtoRequest ds : Arrays.asList(weekDaysSchedule.getDaySchedule())) {
            DayOfWeek dow = parseDayOfWeek(ds.getWeekDay());
            dayOfWeeksSet.add(dow);
            timeStartMap.put(dow, LocalTime.parse(ds.getTimeStart(), DateTimeFormatter.ofPattern("HHmm")));
            timeEndMap.put(dow, LocalTime.parse(ds.getTimeEnd(), DateTimeFormatter.ofPattern("HHmm")));
        }
        // For each day
        for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
            if (dayOfWeeksSet.contains(date.getDayOfWeek())) {
                List<TicketSchedule> ticketScheduleList = new ArrayList<>();
                timeStart = timeStartMap.get(date.getDayOfWeek());
                timeEnd = timeEndMap.get(date.getDayOfWeek());
                for (LocalTime t = timeStart; t.isBefore(timeEnd); t = t.plusMinutes(duration)) {
                    TicketSchedule ticketSchedule = new TicketSchedule();
                    ticketSchedule.setTimeStart(t);
                    ticketSchedule.setTimeEnd(t.plusMinutes(duration));
                    ticketSchedule.setScheduleType(ScheduleType.FREE);
                    ticketScheduleList.add(ticketSchedule);
                }
                // Create day schedule list
                DaySchedule daySchedule = new DaySchedule(doctor, date);
                daySchedule.setTicketSchedule(ticketScheduleList);
                dayScheduleList.add(daySchedule);
            }
        }
        return dayScheduleList;
    }


    /**
     * Convert String to DayOfWeek Day Of Week
     *
     * @param day
     * @return
     * @throws ServerException
     */
    private DayOfWeek parseDayOfWeek(String day) throws ServerException {
        SimpleDateFormat dayFormat = new SimpleDateFormat("E", Locale.US);
        Date date = null;
        try {
            date = dayFormat.parse(day);
        } catch (ParseException e) {
            ServerError er = ServerError.BAD_REQUEST_S;
            er.setMessage(String.format(er.getMessage(), " Incorrect day of week"));
            throw new ServerException(er);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return DayOfWeek.of(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }
}

