package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Admin {
    private int id;
    private User user;
    private String position;


    public Admin(int id, User user, String position) {
        setId(id);
        user.setType(User.Type.ADMINISTRATOR);
        setUser(user);
        setPosition(position);
    }

    public Admin(User user, String position) {
        this(0, user, position);
    }

    public Admin() {
        this(new User(), new String());
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Admin)) return false;
        Admin admin = (Admin) o;
        return getId() == admin.getId() &&
                Objects.equals(getUser(), admin.getUser()) &&
                Objects.equals(getPosition(), admin.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getPosition());
    }
}
