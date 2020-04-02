package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.sql.Date;
import java.util.List;
import java.util.Objects;


public class Schedule {
    private int id;
    private String ticket;
    private Doctor doctor;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private Patient patient;
    private List<Commission> commission;

    public Schedule(int id, String ticket, Doctor doctor, Date date, Time timeStart, Time timeEnd, Patient patient, List<Commission> commission) {
        setId(id);
        setTicket(ticket);
        setDoctor(doctor);
        setDate(date);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setPatient(patient);
        setCommission(commission);
    }

    public Schedule(int id, String ticket, Doctor doctor, Date date, Time timeStart, Time timeEnd, Patient patient) {
        this(0, ticket, doctor, date, timeStart, timeEnd, patient, null);
    }

    public Schedule(String ticket, Doctor doctor, Date date, Time timeStart, Time timeEnd, Patient patient) {
        this(0, ticket, doctor, date, timeStart, timeEnd, patient);
    }

    public Schedule(Doctor doctor, Date date, Time timeStart, Time timeEnd) {
        this(0, null, doctor, date, timeStart, timeEnd, null);
    }

    public Schedule() {

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public List<Commission> getCommission() {
        return commission;
    }

    public void setCommission(List<Commission> commission) {
        this.commission = commission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getTicket(), schedule.getTicket()) &&
                Objects.equals(getDoctor(), schedule.getDoctor()) &&
                Objects.equals(getDate(), schedule.getDate()) &&
                Objects.equals(getTimeStart(), schedule.getTimeStart()) &&
                Objects.equals(getTimeEnd(), schedule.getTimeEnd()) &&
                Objects.equals(getPatient(), schedule.getPatient()) &&
                Objects.equals(getCommission(), schedule.getCommission());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getDoctor(), getDate(), getTimeStart(), getTimeEnd(), getPatient(), getCommission());
    }
}
