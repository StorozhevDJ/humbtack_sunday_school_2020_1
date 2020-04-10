package net.thumbtack.school.hospital.database.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Schedule {
    private int id;
    private Doctor doctor;
    private LocalDate date;
    List<DaySchedule> daySchedule;

    public Schedule(int id, Doctor doctor, LocalDate date, List<DaySchedule> daySchedule) {
        setId(id);
        setDoctor(doctor);
        setDate(date);
        setDaySchedule(daySchedule);
    }

    public Schedule(Doctor doctor, LocalDate date, List<DaySchedule> daySchedule) {
        this(0, doctor, date, daySchedule);
    }

    public Schedule(Doctor doctor, LocalDate date) {
        this(0, doctor, date, null);
    }

    /*public Schedule(String ticket, Doctor doctor, Date date, Time timeStart, Time timeEnd, Patient patient) {
        this(0, ticket, doctor, date, timeStart, timeEnd, patient);
    }*/

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<DaySchedule> getDaySchedule() {
        return daySchedule;
    }

    public void setDaySchedule(List<DaySchedule> daySchedule) {
        this.daySchedule = daySchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Schedule)) return false;
        Schedule schedule = (Schedule) o;
        return getId() == schedule.getId() &&
                Objects.equals(getDoctor(), schedule.getDoctor()) &&
                Objects.equals(getDate(), schedule.getDate()) &&
                Objects.equals(getDaySchedule(), schedule.getDaySchedule());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDoctor(), getDate(), getDaySchedule());
    }
}
