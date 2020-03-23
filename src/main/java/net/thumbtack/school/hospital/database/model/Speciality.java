package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Speciality)) return false;
        Speciality that = (Speciality) o;
        return getId() == that.getId() &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getValue());
    }
}
