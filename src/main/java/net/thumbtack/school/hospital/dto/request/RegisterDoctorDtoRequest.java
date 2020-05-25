package net.thumbtack.school.hospital.dto.request;

/*
{
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество", // необязателен
"speciality": "специальность",
"room": ”номер кабинета”,
"login": "логин",
"password": "пароль",
"dateStart": "дата, начиная с которой создается расписание",
"dateEnd": "дата, до которой создается расписание",
"weekSchedule":
{
“timeStart” :"время начала приема",
“timeEnd” : "время окончания приема",
“weekDays” :
[
день недели 1.. день недели N
]
},
"weekDaysSchedule":
[
daySchedule :{
“weekDay” : “день недели”
“timeStart” :"время начала приема",
“timeEnd” : "время окончания приема",
},
...
],
"duration": время на прием одного пациента в минутах
}
 */


import net.thumbtack.school.hospital.dto.validation.*;

import javax.validation.constraints.NotNull;

public class RegisterDoctorDtoRequest {

    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Patronymic
    private String patronymic;
    @NotNull
    private String speciality;
    @NotNull
    private String room;
    @Login
    private String login;
    @Password
    private String password;
    @Date
    private String dateStart, dateEnd;

    private WeekScheduleDtoRequest weekSchedule;
    private WeekDaysScheduleDtoRequest weekDaysSchedules;

    private int duration;

    public RegisterDoctorDtoRequest() {
    }

    public RegisterDoctorDtoRequest(String firstName, String lastName, String patronymic, @NotNull String speciality, @NotNull String room, String login, String password, String dateStart, String dateEnd, WeekScheduleDtoRequest weekSchedule, int duration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
        this.login = login;
        this.password = password;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekSchedule = weekSchedule;
        this.duration = duration;
    }

    public RegisterDoctorDtoRequest(String firstName, String lastName, String patronymic, @NotNull String speciality, @NotNull String room, String login, String password, String dateStart, String dateEnd, WeekDaysScheduleDtoRequest weekDaysSchedules, int duration) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
        this.login = login;
        this.password = password;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.weekDaysSchedules = weekDaysSchedules;
        this.duration = duration;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public String getRoom() {
        return room;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDateStart() {
        return dateStart;
    }
    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }
    public String getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public WeekScheduleDtoRequest getWeekSchedule() {
        return weekSchedule;
    }

    public void setWeekSchedule(WeekScheduleDtoRequest weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public WeekDaysScheduleDtoRequest getWeekDaysSchedules() {
        return weekDaysSchedules;
    }

    public void setWeekDaysSchedules(WeekDaysScheduleDtoRequest weekDaysSchedules) {
        this.weekDaysSchedules = weekDaysSchedules;
    }
}

