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


import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.dto.validation.Password;

import javax.validation.constraints.NotNull;

public class RegisterDoctorDtoRequest {

    private String firstName;
    private String lastName;
    private String patronymic;
    @NotNull
    private String speciality;
    @NotNull
    private String room;
    @Login
    private String login;
    @Password
    private String password;
    private String dateStart, dateEnd;

    private WeekScheduleDtoRequest weekSchedule;
    private WeekDaysScheduleDtoRequest weekDaysSchedules;

    private int duration;


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

