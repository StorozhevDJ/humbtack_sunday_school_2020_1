package net.thumbtack.school.hospital.dto.response.getticket;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetTicketDto {
    private String ticket;
    private String room;
    private String date;
    private String time;
    // For doctor reception
    private Integer doctorId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    // For commission
    private List<GetTicketCommissionDto> doctorsCommissionList;

    public GetTicketDto() {
    }

    public GetTicketDto(String ticket, String room, String date, String time, int doctorId, String firstName,
                        String lastName, String patronymic, String speciality) {
        super();
        this.ticket = ticket;
        this.room = room;
        this.date = date;
        this.time = time;
        this.doctorId = doctorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
    }

    public GetTicketDto(String ticket, String room, String date, String time, List<GetTicketCommissionDto> doctorsCommissionList) {
        this.ticket = ticket;
        this.room = room;
        this.date = date;
        this.time = time;
        this.doctorsCommissionList = doctorsCommissionList;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public List<GetTicketCommissionDto> getDoctorsCommissionList() {
        return doctorsCommissionList;
    }

    public void setDoctorsCommissionList(List<GetTicketCommissionDto> doctorsCommissionList) {
        this.doctorsCommissionList = doctorsCommissionList;
    }
}
