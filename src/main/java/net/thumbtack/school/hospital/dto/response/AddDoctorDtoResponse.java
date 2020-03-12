package net.thumbtack.school.hospital.dto.response;

/*
{
"id": "идентификационный номер",
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество",
"speciality": "специальность",
"room": ”номер кабинета”,
"schedule": [
{
“date” :"дата приема",
“daySchedule”: [
{
time : “время”,
patient :
{
"patientId": идентификационный номер пациента, получившего талон на это время,
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество", // необязателен
"email": "адрес",
"address": "домашний адрес, одной строкой",
"phone": "номер",
}
}
...
]
}
...
]
}
 */

public class AddDoctorDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    private String room;
    private String login;
    private String password;
    private String dateStart;
    private String dateEnd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


}
