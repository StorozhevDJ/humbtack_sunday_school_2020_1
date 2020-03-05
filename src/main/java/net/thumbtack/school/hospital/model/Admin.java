package net.thumbtack.school.hospital.model;

import java.util.Objects;

public class Admin {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String login;
    private String password;
    private String token;


    public Admin(int id, String firstName, String lastName, String patronymic, String position, String login, String password, String token) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setPosition(position);
        setLogin(login);
        setPassword(password);
        setToken(token);
    }

    public Admin(String firstName, String lastName, String patronymic, String position, String login, String password, String token) {
        this(0, firstName, lastName, patronymic, position, login, password, token);
    }

    public Admin() {
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(firstName, admin.firstName) &&
                Objects.equals(lastName, admin.lastName) &&
                Objects.equals(patronymic, admin.patronymic) &&
                Objects.equals(position, admin.position) &&
                Objects.equals(login, admin.login) &&
                Objects.equals(password, admin.password) &&
                Objects.equals(token, admin.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, patronymic, position, login, password, token);
    }
}
