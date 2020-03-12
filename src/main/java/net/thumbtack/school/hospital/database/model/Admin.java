package net.thumbtack.school.hospital.database.model;

public class Admin {
    private int id;
    private User user;
    private String position;


    public Admin(int id, User user, String position) {
        setId(id);
        user.setType("admin");
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Admin))
            return false;
        Admin other = (Admin) obj;
        if (id != other.id)
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }


}
