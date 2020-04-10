package net.thumbtack.school.hospital.database.model;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

public class Commission {
    private int id;
    private String ticket;
    private Time timeStart;
    private Time timeEnd;
    private Patient patient;
    private List<Doctor> doctorList;

    public Commission(int id, String ticket, Time timeStart, Time timeEnd, Patient patient, List<Doctor> doctorList) {
        setId(id);
        setTicket(ticket);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setPatient(patient);
        setDoctorList(doctorList);
    }

    public Commission() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
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

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commission)) return false;
        Commission that = (Commission) o;
        return getId() == that.getId() &&
                Objects.equals(getTicket(), that.getTicket()) &&
                Objects.equals(getTimeStart(), that.getTimeStart()) &&
                Objects.equals(getTimeEnd(), that.getTimeEnd()) &&
                Objects.equals(getPatient(), that.getPatient()) &&
                Objects.equals(getDoctorList(), that.getDoctorList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getTimeStart(), getTimeEnd(), getPatient(), getDoctorList());
    }
}
