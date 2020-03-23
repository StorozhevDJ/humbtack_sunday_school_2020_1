package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Doctor {
    private int id;
    private User user;
    //private Speciality speciality;
    private String speciality;
    private String room;


    public Doctor(int id, User user, String speciality, String room) {
        setId(id);
        user.setType("doctor");
        setUser(user);
        setSpeciality(speciality);
        setRoom(room);
    }

    public Doctor(User user, String speciality, String room) {
        this(0, user, speciality, room);
    }

    public Doctor() {
        this(new User(), new String(), new String());
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }*/

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return getId() == doctor.getId() &&
                Objects.equals(getUser(), doctor.getUser()) &&
                Objects.equals(getSpeciality(), doctor.getSpeciality()) &&
                Objects.equals(getRoom(), doctor.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getSpeciality(), getRoom());
    }
}
