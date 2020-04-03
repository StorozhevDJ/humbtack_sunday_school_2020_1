package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Patient {
    private int id;
    private User user;
    private String email;
    private String address;
    private String phone;
    // а здесь Set или List из его талонов


    public Patient(int id, User user, String email, String address, String phone) {
        setId(id);
        user.setType(User.Type.PATIENT);
        setUser(user);
        setEmail(email);
        setAddress(address);
        setPhone(phone);
    }

    public Patient(User user, String email, String address, String phone) {
        this(0, user, email, address, phone);
    }

    public Patient() {
        this(new User(), new String(), new String(), new String());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return getId() == patient.getId() &&
                Objects.equals(getUser(), patient.getUser()) &&
                Objects.equals(getEmail(), patient.getEmail()) &&
                Objects.equals(getAddress(), patient.getAddress()) &&
                Objects.equals(getPhone(), patient.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getEmail(), getAddress(), getPhone());
    }
}
