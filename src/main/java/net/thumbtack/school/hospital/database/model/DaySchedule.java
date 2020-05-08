package net.thumbtack.school.hospital.database.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class DaySchedule {
    private int id;
    private Doctor doctor;
    private LocalDate date;
    private List<TicketSchedule> ticketSchedule;

    public DaySchedule(int id, Doctor doctor, LocalDate date, List<TicketSchedule> ticketSchedule) {
        setId(id);
        setDoctor(doctor);
        setDate(date);
        setTicketSchedule(ticketSchedule);
    }

    public DaySchedule(Doctor doctor, LocalDate date, List<TicketSchedule> ticketSchedule) {
        this(0, doctor, date, ticketSchedule);
    }

    public DaySchedule(Doctor doctor, LocalDate date) {
        this(0, doctor, date, null);
    }

    public DaySchedule() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<TicketSchedule> getTicketSchedule() {
        return ticketSchedule;
    }

    public void setTicketSchedule(List<TicketSchedule> ticketSchedule) {
        this.ticketSchedule = ticketSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaySchedule)) return false;
        DaySchedule daySchedule = (DaySchedule) o;
        return getId() == daySchedule.getId() &&
                Objects.equals(getDoctor(), daySchedule.getDoctor()) &&
                Objects.equals(getDate(), daySchedule.getDate()) &&
                Objects.equals(getTicketSchedule(), daySchedule.getTicketSchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDoctor(), getDate(), getTicketSchedule());
    }
}
