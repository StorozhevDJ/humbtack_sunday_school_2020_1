package net.thumbtack.school.hospital.database.model;

public enum UserType {
    ADMINISTRATOR("Administrator"),
    DOCTOR("Doctor"),
    PATIENT("Patient");

    private String text;

    UserType(String code) {
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
