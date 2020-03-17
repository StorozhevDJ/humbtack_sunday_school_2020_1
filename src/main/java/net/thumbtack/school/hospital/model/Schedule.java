package net.thumbtack.school.hospital.model;

import java.sql.Time;
import java.util.Date;
import java.util.Objects;

public class Schedule {
    private int id;
    private Doctor doctor;
    private String room;
    private Date date;
    private Time time;
    private short duration;
    private Patient patient;

    public Schedule(int id, Doctor doctor, String room, Date date, Time time, Patient patient) {
        setId(id);
        setDoctor(doctor);
        setRoom(room);
        setDate(date);
        setTime(time);
        setPatient(patient);
    }

    public Schedule(Doctor doctor, String room, Date date, Time time, Patient patient) {
        this(0, doctor, room, date, time, patient);
    }

    public Schedule() {
        this(0, null, null, null, null, null);
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

    public short getDuration() {
        return duration;
    }

    public void setDuration(short duration) {
        this.duration = duration;
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
