package net.thumbtack.school.hospital.service;

import net.thumbtack.school.hospital.database.dao.*;
import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.*;
import net.thumbtack.school.hospital.dto.response.EditScheduleDtoResponse;
import net.thumbtack.school.hospital.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;
import net.thumbtack.school.hospital.dto.response.StatisticDtoResponse;
import net.thumbtack.school.hospital.mapper.AdminMapper;
import net.thumbtack.school.hospital.mapper.DoctorMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    private final AdminDao adminDao;
    private final UserDao userDao;
    private final DoctorDao doctorDao;
    private final ScheduleDao scheduleDao;
    private final PatientDao patientDao;

    @Autowired
    public AdminService(AdminDao adminDao, UserDao userDao, DoctorDao doctorDao, ScheduleDao scheduleDao, PatientDao patientDao) {
        this.adminDao = adminDao;
        this.userDao = userDao;
        this.doctorDao = doctorDao;
        this.scheduleDao = scheduleDao;
        this.patientDao = patientDao;
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
        Admin admin = adminDao.getByToken(cookie);
        if (admin == null) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        admin = adminDao.insert(AdminMapper.convertToEntity(registerAdminDtoRequest));
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
        Admin admin = adminDao.getByToken(cookie);
        if (admin == null) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // Update Admin position, if required
        if (!admin.getPosition().equals(editAdminDtoRequest.getPosition())) {
            admin.setPosition(editAdminDtoRequest.getPosition());
            adminDao.update(admin);
        }
        // Update user info
        User user = AdminMapper.convertToEntity(editAdminDtoRequest, admin.getUser().getLogin()).getUser();
        user.setId(admin.getUser().getId());
        userDao.update(user);
        admin.setUser(user);
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
        User user = userDao.getByToken(cookie);
        if ((user == null) || (user.getUserType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // Insert new doctor to DB
        Doctor doctor = DoctorMapper.convertToEntity(registerDtoRequest);
        doctor = doctorDao.insert(doctor);

        LocalDate dateStart = LocalDate.parse(registerDtoRequest.getDateStart(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate dateEnd = LocalDate.parse(registerDtoRequest.getDateEnd(), DateTimeFormatter.ofPattern(DATE_FORMAT));
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
     * Edit doctor schedule (and add new)
     *
     * @param editDto
     * @param doctorId
     * @param cookie
     * @return
     * @throws ServerException
     */
    public EditScheduleDtoResponse editScheduleDoctor(EditScheduleDtoRequest editDto, int doctorId, String cookie) throws ServerException {
        User user = userDao.getByToken(cookie);
        if ((user == null) || (user.getUserType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        LocalDate dateStart = LocalDate.parse(editDto.getDateStart(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate dateEnd = LocalDate.parse(editDto.getDateEnd(), DateTimeFormatter.ofPattern(DATE_FORMAT));

        Doctor doctor = doctorDao.getByDoctorId(doctorId);
        if (doctor == null) {
            throw new ServerException(ServerError.DOCTOR_ID_INVALID);
        }
        //List<Integer> ticketList = scheduleDao.getCountFreeSchedule(Arrays.asList(doctorId), date, timeStart, timeEnd);

        List<DaySchedule> dayScheduleList;
        // Create from WeekSchedule
        if (editDto.getWeekSchedule() != null) {
            dayScheduleList = createWeekSchedule(editDto.getWeekSchedule(), dateStart, dateEnd, editDto.getDuration(), doctor);
        }
        // Create from WeekDaysSchedule
        else if (editDto.getWeekDaysSchedules() != null) {
            // For each day of the week
            dayScheduleList = createWeekDaysSchedule(editDto.getWeekDaysSchedules(), dateStart, dateEnd, editDto.getDuration(), doctor);
        } else {
            ServerError er = ServerError.BAD_REQUEST_S;
            er.setMessage(String.format(er.getMessage(), " Empty schedule"));
            throw new ServerException(er);
        }
        doctor.setDayScheduleList(dayScheduleList);

        scheduleDao.updateSchedule(doctor);

        return DoctorMapper.convertToDto(doctor, dayScheduleList);
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
        User user = userDao.getByToken(cookie);
        if ((user == null) || (user.getUserType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        // Delete doctor
        LocalDate date = LocalDate.parse(deleteDoctorDtoRequest.getDate(), DateTimeFormatter.ofPattern(DATE_FORMAT));
        List<DaySchedule> dayScheduleList = scheduleDao.getByDoctorId(doctorId, date, date.plusMonths(2));
        scheduleDao.deleteSchedule(doctorId, date);
        // Send sms/email
        Map<String, String> smsMap = new HashMap<>();
        Map<String, String> emailMap = new HashMap<>();
        for (DaySchedule daySchedule : dayScheduleList) {
            for (TicketSchedule ticketSchedule : daySchedule.getTicketSchedule()) {
                if (ticketSchedule.getPatient() != null) {
                    String text = "Your ticket # " + ticketSchedule.getTicket() + " to " + daySchedule.getDate() + " is canceled.";
                    smsMap.put(ticketSchedule.getPatient().getPhone(), text);
                    emailMap.put(ticketSchedule.getPatient().getEmail(), text);
                }
            }
        }
        SendSmsThread sendSmsThread = new SendSmsThread(smsMap);
        SendEmailThread sendEmailThread = new SendEmailThread(emailMap);
        sendSmsThread.run();
        sendEmailThread.run();
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
                timeStart = LocalTime.parse(weekSchedule.getTimeStart(), DateTimeFormatter.ofPattern(TIME_FORMAT));
                timeEnd = LocalTime.parse(weekSchedule.getTimeEnd(), DateTimeFormatter.ofPattern(TIME_FORMAT));
                // For each hour in day
                List<TicketSchedule> ticketScheduleList = new ArrayList<>();
                for (LocalTime t = timeStart; t.isBefore(timeEnd); t = t.plusMinutes(duration)) {
                    ticketScheduleList.add(new TicketSchedule(t, t.plusMinutes(duration), ScheduleType.FREE));
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
            timeStartMap.put(dow, LocalTime.parse(ds.getTimeStart(), DateTimeFormatter.ofPattern(TIME_FORMAT)));
            timeEndMap.put(dow, LocalTime.parse(ds.getTimeEnd(), DateTimeFormatter.ofPattern(TIME_FORMAT)));
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
        Date date;
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

    /**
     * Tickets count statistic for doctor or patient
     *
     * @param cookie
     * @param doctor
     * @param patient
     * @param startDate
     * @param endDate
     * @return
     * @throws ServerException
     */
    public StatisticDtoResponse statistic(String cookie, Integer doctor, Integer patient, String startDate, String endDate) throws ServerException {
        User user = userDao.getByToken(cookie);
        if ((user == null) || (user.getUserType() != UserType.ADMINISTRATOR)) {
            throw new ServerException(ServerError.ACCESS_DENIED);
        }
        List<Statistic> stat;
        LocalDate dateStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        LocalDate dateEnd = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(DATE_FORMAT));
        int freeTickets = 0, receptionTickets = 0, commissionTickets = 0;
        if (doctor != null) {
            stat = doctorDao.getTicketCount(doctor, dateStart, dateEnd);
        } else if (patient != null) {
            stat = patientDao.getTicketCount(patient, dateStart, dateEnd);
        } else {
            ServerError er = ServerError.BAD_REQUEST_S;
            er.setMessage(String.format(er.getMessage(), " Empty doctor and patient ID"));
            throw new ServerException(er);
        }
        for (Statistic s : stat) {
            switch (s.getType()) {
                case FREE:
                    freeTickets = s.getCount();
                    break;
                case RECEPTION:
                    receptionTickets = s.getCount();
                    break;
                case COMMISSION:
                    commissionTickets = s.getCount();
                    break;
            }
        }
        return new StatisticDtoResponse(patient, doctor, freeTickets, receptionTickets, commissionTickets);
    }


    class SendSmsThread extends Thread {

        private final Map<String, String> smsMap;

        public SendSmsThread(Map<String, String> smsMap) {
            this.smsMap = smsMap;
        }

        public void run() {
            try {
                smsMap.forEach((p, t) -> sendSms(t, p));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println("Thread exit.");
        }
    }

    class SendEmailThread extends Thread {

        private final Map<String, String> emailMap;

        public SendEmailThread(Map<String, String> emailMap) {
            this.emailMap = emailMap;
        }

        public void run() {
            try {
                emailMap.forEach((p, t) -> sendEmail(t, p));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            System.out.println("Thread exit.");
        }
    }

    private void sendSms(String text, String phone) {
        LOGGER.info("Send SMS to phone: {}. Text: {}", phone, text);
    }

    private void sendEmail(String text, String email) {
        LOGGER.info("Send email to {}. Text: {}", email, text);
    }
}

