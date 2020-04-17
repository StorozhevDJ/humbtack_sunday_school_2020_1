package net.thumbtack.school.hospital.database.model;

import java.util.Objects;

public class Room {
    private int id;
    private String number;

    public Room(int id, String number) {
        this.id = id;
        this.number = number;
    }

    public Room(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return getId() == room.getId() &&
                Objects.equals(getNumber(), room.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber());
    }
}
