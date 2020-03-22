package net.thumbtack.school.hospital.database.model;

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
}
