package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.sql.Date;
import java.util.Objects;


public class Schedule {
    private int id;
    private String ticket;
    //private int doctorId;
    private Doctor doctor;
    private Date date;
    private Time time;
    private Time timeEnd;
    //private Integer patientId;
    private Patient patient;
    private String room;

    public Schedule(int id, String ticket, Doctor doctor, Date date, Time time, Time timeEnd, Patient patient, String room) {
        setId(id);
        setTicket(ticket);
        //setDoctorId(doctorId);
        setDoctor(doctor);
        setDate(date);
        setTime(time);
        setTimeEnd(timeEnd);
        //setPatientId(patientId);
        setPatient(patient);
        setRoom(room);
    }

    public Schedule(String ticket, Doctor doctor, Date date, Time time, Time timeEnd, Patient patient, String room) {
        this(0, ticket, doctor, date, time, timeEnd, patient, room);
    }

    public Schedule(Doctor doctor, Date date, Time time, Time timeEnd, String room) {
        this(0, null, doctor, date, time, timeEnd, null, room);
    }

    public Schedule() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }*/

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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

    /*public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }*/

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
                //getDoctorId() == schedule.getDoctorId() &&
                getDoctor() == schedule.getDoctor() &&
                getTicket().equals(schedule.getTicket()) &&
                getDate().equals(schedule.getDate()) &&
                getTime().equals(schedule.getTime()) &&
                getTimeEnd().equals(schedule.getTimeEnd()) &&
                //getPatientId().equals(schedule.getPatientId()) &&
                getPatient().equals(schedule.getPatient()) &&
                getRoom().equals(schedule.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getDoctor(), getDate(), getTime(), getTimeEnd(), getPatient(), getRoom());
    }
}
