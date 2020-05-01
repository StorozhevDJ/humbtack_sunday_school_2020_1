package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.ApplicationProperties;
import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.dao.DayScheduleDao;
import net.thumbtack.school.hospital.database.dao.DoctorDao;
import net.thumbtack.school.hospital.database.dao.UserDao;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.DeleteDoctorDtoResponse;
import net.thumbtack.school.hospital.dto.response.GetServerSettingsDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.mapper.AdminMapper;
import net.thumbtack.school.hospital.mapper.DoctorMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Transactional(rollbackFor = ServerException.class)
public class AdminService {

    private AdminDao adminDao;
    private UserDao userDao;
    private DoctorDao doctorDao;
    private DayScheduleDao dayScheduleDao;

    @Autowired
    ApplicationProperties prop;

    @Autowired
    public AdminService(AdminDao adminDao, UserDao userDao, DoctorDao doctorDao, DayScheduleDao dayScheduleDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.doctorDao = doctorDao;
        this.dayScheduleDao = dayScheduleDao;
    }

    public LoginDtoResponse registerAdmin (RegisterAdminDtoRequest registerAdminDtoRequest, String cookie) throws ServerException {
        Admin admin = AdminMapper.convertToEntity(registerAdminDtoRequest);
        admin = adminDao.insert(admin);
        // REVU см. REVU в классе Admin
        admin.getUser().setSession(new Session(admin.getUser().getId(), cookie));
        userDao.logIn(admin.getUser());
        return AdminMapper.convertToDto(admin);
    }

    public LoginDtoResponse editAdmin (EditAdminDtoRequest editAdminDtoRequest, String cookie) throws ServerException {
        User user = userDao.getByToken(new Session(cookie));
        if ((user != null) && (user.getType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // REVU не надо тут новый Admin создавать
        // получите Admin по cookie и измените его поля, потом запишите
        // кстати, записать надо не только поля user, но и поля admin 
        Admin admin = AdminMapper.convertToEntity(editAdminDtoRequest, user.getLogin());
        user.setPassword(admin.getUser().getPassword());
        userDao.update(user);
        return AdminMapper.convertToDto(admin);
    }
// TODO add schedule
    public LoginDtoResponse registerDoctor (RegisterDoctorDtoRequest registerDtoRequest, String cookie) throws ServerException {
        User user = userDao.getByToken(new Session(cookie));
        if ((user != null) && (user.getType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        Doctor doctor = DoctorMapper.convertToEntity(registerDtoRequest);
        doctor = doctorDao.insert(doctor);

        LocalDate dateStart = LocalDate.parse(registerDtoRequest.getDateStart(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalDate dateEnd = LocalDate.parse(registerDtoRequest.getDateEnd(), DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalTime timeStart, timeEnd;
        if (dateEnd.isAfter(dateStart.plusMonths(2))) {
            dateEnd = dateStart;
        }
        List<String> daysList;
        List<DaySchedule> dayScheduleList = new ArrayList<>();

        // Create from WeekSchedule
        // REVU выделите private метод,Ю который из реквеста делает детальное расписание
        if (registerDtoRequest.getWeekSchedule() != null) {
            if (registerDtoRequest.getWeekSchedule().getWeekDays() != null) {
                daysList = Arrays.asList(registerDtoRequest.getWeekSchedule().getWeekDays());
            } else {    //Set default Weekdays work
                daysList = Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri");
            }
            // For each day of the week
            for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
            	// REVU Лучше не список дней
            	// https://docs.oracle.com/javase/8/docs/api/java/time/DayOfWeek.html
            	// Поотом сделайте EnumSet<DayOfWeek> и в него добавьте нужные дни недели
            	// ну и по нему contains
                if (daysList.contains(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US))) {
                    timeStart = LocalTime.parse(registerDtoRequest.getWeekSchedule().getTimeStart(), DateTimeFormatter.ofPattern("HHmm"));
                    timeEnd = LocalTime.parse(registerDtoRequest.getWeekSchedule().getTimeEnd(), DateTimeFormatter.ofPattern("HHmm"));
                    // For each hour in day
                    List<TicketSchedule> ticketScheduleList = new ArrayList<>();
                    for (LocalTime t = timeStart; t.isBefore(timeEnd); t = t.plusMinutes(registerDtoRequest.getDuration())) {
                        TicketSchedule ticketSchedule = new TicketSchedule();
                        ticketSchedule.setTimeStart(t);
                        ticketSchedule.setTimeEnd(t.plusMinutes(registerDtoRequest.getDuration()));
                        ticketSchedule.setScheduleType(ScheduleType.FREE);
                        ticketScheduleList.add(ticketSchedule);
                    }
                    DaySchedule daySchedule = new DaySchedule(doctor, date);
                    daySchedule.setTicketSchedule(ticketScheduleList);
                    dayScheduleList.add(daySchedule);
                }
            }
            dayScheduleDao.createSchedule(dayScheduleList);
        }
        // Create from WeekDaysSchedule
        else if (registerDtoRequest.getWeekDaysSchedules() != null) {
            // For each day of the week
            for (LocalDate date = dateStart; date.isBefore(dateEnd.plusDays(1)); date = date.plusDays(1)) {
                List<RegisterDoctorDtoRequest.WeekDaysSchedule.DaySchedule> dsl = Arrays.asList(registerDtoRequest.getWeekDaysSchedules().getDaySchedule());
                for (RegisterDoctorDtoRequest.WeekDaysSchedule.DaySchedule ds:dsl) {
                	// REVU аналогично
                    if(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.US).equals(ds.getWeekDay())) {
                        List<TicketSchedule> ticketScheduleList = new ArrayList<>();
                        timeStart = LocalTime.parse(registerDtoRequest.getWeekSchedule().getTimeStart(), DateTimeFormatter.ofPattern("HHmm"));
                        timeEnd = LocalTime.parse(registerDtoRequest.getWeekSchedule().getTimeEnd(), DateTimeFormatter.ofPattern("HHmm"));
                        for (LocalTime t = timeStart; t.isBefore(timeEnd); t = t.plusMinutes(registerDtoRequest.getDuration())) {
                            TicketSchedule ticketSchedule = new TicketSchedule();
                            ticketSchedule.setTimeStart(t);
                            ticketSchedule.setTimeEnd(t.plusMinutes(registerDtoRequest.getDuration()));
                            ticketSchedule.setScheduleType(ScheduleType.FREE);
                            ticketScheduleList.add(ticketSchedule);
                        }
                        DaySchedule daySchedule = new DaySchedule(doctor, date);
                        daySchedule.setTicketSchedule(ticketScheduleList);
                        dayScheduleList.add(daySchedule);
                    }

                }
            }
        } else {
            ServerError er = ServerError.BAD_REQUEST_S;
            er.setMessage(String.format(er.getMessage(), " Empty schedule"));
            throw new ServerException(er);
        }
        return DoctorMapper.convertToDto(doctor);
    }

    public LoginDtoResponse editScheduleDoctor(EditScheduleDtoRequest editDto, long doctorId, String cookie) throws ServerException {
        LoginDtoResponse dto = null;

        return dto;
    }

    public DeleteDoctorDtoResponse deleteDoctor (DeleteDoctorDtoRequest deleteDoctorDtoRequest, long doctorId, String cookie) throws ServerException {

        DeleteDoctorDtoResponse dto = null;

        return dto;
    }

    public GetServerSettingsDtoResponse getServerSettings (String cookie) throws ServerException {
        GetServerSettingsDtoResponse dto = new GetServerSettingsDtoResponse();
        dto.setMaxNameLength(prop.getMaxNameLength());
        dto.setMinPasswordLength(prop.getMinPasswordLength());
        return dto;
    }
}
