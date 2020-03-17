package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Schedule {
    private int id;
    private Doctor doctor;
    private String room;
    private Date date;
    private Time time;
    private Time timeEnd;
    private Patient patient;

    public Schedule(int id, Doctor doctor, String room, Date date, Time time, Time timeEnd, Patient patient) {
        setId(id);
        setDoctor(doctor);
        setRoom(room);
        setDate(date);
        setTime(time);
        setTimeEnd(timeEnd);
        setPatient(patient);
    }

    public Schedule(Doctor doctor, String room, Date date, Time time, Time timeEnd, Patient patient) {
        this(0, doctor, room, date, time, timeEnd, patient);
    }

    public Schedule() {
        this(null, null, null, null, null, null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id &&
                Objects.equals(doctor, schedule.doctor) &&
                Objects.equals(room, schedule.room) &&
                Objects.equals(date, schedule.date) &&
                Objects.equals(time, schedule.time) &&
                Objects.equals(patient, schedule.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, room, date, time, patient);
    }

}
