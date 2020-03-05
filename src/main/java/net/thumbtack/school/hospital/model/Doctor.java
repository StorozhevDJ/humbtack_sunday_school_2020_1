package net.thumbtack.school.hospital.model;

import java.util.Objects;

public class Doctor {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String speciality;
    private String login;
    private String password;
    private String token;


    public Doctor(int id, String firstName, String lastName, String patronymic, String speciality, String login, String password, String token) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setSpeciality(speciality);
        setLogin(login);
        setPassword(password);
        setToken(token);
    }

    public Doctor(String firstName, String lastName, String patronymic, String speciality, String login, String password, String token) {
        this(0, firstName, lastName, patronymic, speciality, login, password, token);
    }

    public Doctor() {
        this(new String(), new String(), new String(), new String(), new String(), new String(), new String());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(firstName, doctor.firstName) &&
                Objects.equals(lastName, doctor.lastName) &&
                Objects.equals(patronymic, doctor.patronymic) &&
                Objects.equals(speciality, doctor.speciality) &&
                Objects.equals(login, doctor.login) &&
                Objects.equals(password, doctor.password) &&
                Objects.equals(token, doctor.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, speciality, login, password, token);
    }
}
