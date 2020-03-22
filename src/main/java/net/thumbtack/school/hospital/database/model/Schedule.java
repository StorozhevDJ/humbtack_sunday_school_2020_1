package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.sql.Date;


public class Schedule {
    private int id;
    private String ticket;
    private int doctorId;
    private Date date;
    private Time time;
    private Time timeEnd;
    private int patientId;

    public Schedule(int id, String ticket, int doctorId, Date date, Time time, Time timeEnd, int patientId) {
        setId(id);
        setTicket(ticket);
        setDoctorId(doctorId);
        setDate(date);
        setTime(time);
        setTimeEnd(timeEnd);
        setPatientId(patientId);
    }

    public Schedule(String ticket, int doctorId, Date date, Time time, Time timeEnd, int patientId) {
        this(0, ticket, doctorId, date, time, timeEnd, patientId);
    }

    public Schedule() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
