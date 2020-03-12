package net.thumbtack.school.hospital.database.model;

public class Doctor {
    private int id;
    private User user;
    private Speciality speciality;


    public Doctor(int id, User user, String specialityn) {
        setId(id);
        setUser(user);
        setSpeciality(speciality);
    }

    public Doctor(User user, String speciality) {
        this(0, user, speciality);
    }

    public Doctor() {
        this(new User(), new String());
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

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Doctor))
            return false;
        Doctor other = (Doctor) obj;
        if (id != other.id)
            return false;
        if (speciality == null) {
            if (other.speciality != null)
                return false;
        } else if (!speciality.equals(other.speciality))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }


}
