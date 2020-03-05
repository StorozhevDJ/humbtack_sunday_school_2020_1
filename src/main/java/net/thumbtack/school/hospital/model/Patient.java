package net.thumbtack.school.hospital.model;

import java.util.Objects;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String address;
    private String phone;
    private String login;
    private String password;
    private String token;

    public Patient(int id, String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, String token) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setEmail(email);
        setAddress(address);
        setPhone(phone);
        setLogin(login);
        setPassword(password);
        setToken(token);
    }

    public Patient(String firstName, String lastName, String patronymic, String email, String address, String phone, String login, String password, String token) {
        this(0, firstName, lastName, patronymic, email, address, phone, login, password, token);
    }

    public Patient() {
        this(new String(), new String(), new String(), new String(), new String(), new String(), new String(), new String(), new String());
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
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(firstName, patient.firstName) &&
                Objects.equals(lastName, patient.lastName) &&
                Objects.equals(patronymic, patient.patronymic) &&
                Objects.equals(email, patient.email) &&
                Objects.equals(address, patient.address) &&
                Objects.equals(phone, patient.phone) &&
                Objects.equals(login, patient.login) &&
                Objects.equals(password, patient.password) &&
                Objects.equals(token, patient.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, email, address, phone, login, password, token);
    }
}
