package net.thumbtack.school.hospital.database.model;

public class Speciality {
    private int id;
    private String value;


    public Speciality(int id, String value) {
        setId(id);
        setValue(value);
    }

    public Speciality(String value) {
        this(0, value);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Speciality))
            return false;
        Speciality other = (Speciality) obj;
        if (id != other.id)
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }


}
