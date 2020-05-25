package net.thumbtack.school.hospital.database.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

public class Commission {
    private int id;
    private String ticket;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private LocalDate date;
    private String room;
    private Integer patientId;
    private Set<Doctor> doctorSet;


    public Commission(int id, String ticket, LocalTime timeStart, LocalTime timeEnd, LocalDate date, String room, Integer patientId, Set<Doctor> doctorSet) {
        setId(id);
        setTicket(ticket);
        setTimeStart(timeStart);
        setTimeEnd(timeEnd);
        setDate(date);
        setRoom(room);
        setPatientId(patientId);
        setDoctorSet(doctorSet);
    }

    public Commission(String ticket, LocalTime timeStart, LocalTime timeEnd, LocalDate date, String room, Integer patientId, Set<Doctor> doctorSet) {
        this(0, ticket, timeStart, timeEnd, date, room, patientId, doctorSet);
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

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Set<Doctor> getDoctorSet() {
        return doctorSet;
    }

    public void setDoctorSet(Set<Doctor> doctorSet) {
        this.doctorSet = doctorSet;
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
                Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getRoom(), that.getRoom()) &&
                Objects.equals(getPatientId(), that.getPatientId()) &&
                Objects.equals(getDoctorSet(), that.getDoctorSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTicket(), getTimeStart(), getTimeEnd(), getDate(), getRoom(), getPatientId(), getDoctorSet());
    }
}
