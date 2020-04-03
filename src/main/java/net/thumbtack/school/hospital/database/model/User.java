package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class User {

	// REVU вынесите в отдельный файл
    public enum Type {
        ADMINISTRATOR("Administrator"),
        DOCTOR("Doctor"),
        PATIENT("Patient");

    	// REVU не нужно, бесполезно 
        private String text;

        Type(String code) {
            this.text = code;
        }

        public String toString() {
            return text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class Session {
        private String token;

        public Session(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Type type;
    private String login;
    private String password;
    // REVU здесь быть не должно. Сделайте класс Session
    private Session session = new Session(null);


    public User(int id, String firstName, String lastName, String patronymic, Type type, String login, String password, Session session) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setType(type);
        setLogin(login);
        setPassword(password);
        setSession(session);
    }

    public User(String firstName, String lastName, String patronymic, Type type, String login, String password, Session session) {
        this(0, firstName, lastName, patronymic, type, login, password, session);
    }

    /**
     * @param firstName  - Имя
     * @param lastName   - Фамилия
     * @param patronymic - Отчество
     * @param login      - Логин для входа
     * @param password   - Пароль
     * @param session    Session ID
     */
    public User(String firstName, String lastName, String patronymic, String login, String password, Session session) {
        this(0, firstName, lastName, patronymic, null, login, password, session);
    }

    public User(String login, String password, Session session) {
        this(0, null, null, null, null, login, password, session);
    }

    public User() {
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getFirstName(), user.getFirstName()) &&
                Objects.equals(getLastName(), user.getLastName()) &&
                Objects.equals(getPatronymic(), user.getPatronymic()) &&
                Objects.equals(getType(), user.getType()) &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getSession(), user.getSession());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getPatronymic(), getType(), getLogin(), getPassword(), getSession());
    }
}
