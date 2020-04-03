package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Commission {
    private int id;
    private int scheduleId;
    // REVU List<Doctor> doctors;
    private int doctorId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Commission(int id, int scheduleId, int doctorId) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
    }

    public Commission(int scheduleId, int doctorId) {
        this(0, scheduleId, doctorId);
    }

    public Commission() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commission)) return false;
        Commission that = (Commission) o;
        return getId() == that.getId() &&
                getScheduleId() == that.getScheduleId() &&
                getDoctorId() == that.getDoctorId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScheduleId(), getDoctorId());
    }
}
