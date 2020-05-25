package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Session {
    private int userId;
    private String token;

    public Session() {
    }

    public Session(String token) {
        this.token = token;
    }
    public Session(int userId, String token) {
        this.setUserId(userId);
        this.setToken(token);
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        if (!(o instanceof Session)) return false;
        Session session = (Session) o;
        return getUserId() == session.getUserId() &&
                Objects.equals(getToken(), session.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getToken());
    }
}

