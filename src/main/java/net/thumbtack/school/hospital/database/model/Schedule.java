package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.sql.Date;
import java.util.Objects;


public class Schedule {
    private int id;
    private String ticket;
    private int doctorId;
    private Date date;
    private Time time;
    private Time timeEnd;
    private Integer patientId;
    private String room;

    public Schedule(int id, String ticket, int doctorId, Date date, Time time, Time timeEnd, Integer patientId, String room) {
        setId(id);
        setTicket(ticket);
        setDoctorId(doctorId);
        setDate(date);
        setTime(time);
        setTimeEnd(timeEnd);
        setPatientId(patientId);
        setRoom(room);
    }

    public Schedule(String ticket, int doctorId, Date date, Time time, Time timeEnd, Integer patientId, String room) {
        this(0, ticket, doctorId, date, time, timeEnd, patientId, room);
    }

    public Schedule(int doctorId, Date date, Time time, Time timeEnd, String room) {
        this(0, null, doctorId, date, time, timeEnd, null, room);
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                getDoctorId() == schedule.getDoctorId() &&
                getTicket().equals(schedule.getTicket()) &&
                getDate().equals(schedule.getDate()) &&
                getTime().equals(schedule.getTime()) &&
                getTimeEnd().equals(schedule.getTimeEnd()) &&
                getPatientId().equals(schedule.getPatientId()) &&
                getRoom().equals(schedule.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getDoctorId(), getDate(), getTime(), getTimeEnd(), getPatientId(), getRoom());
    }
}
