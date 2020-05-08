package net.thumbtack.school.hospital.serverexception;

public enum ServerError {

    LOGIN_ALREADY_EXISTS("login", "User login already exists!"),
    LOGIN_OR_PASSWORD_INVALID("login", "User Login or password is incorrect!"),
    TOKEN_INVALID("login", "Token invalid!"),
    USER_ID_INVALID("user", "User ID invalid!"),
    PATIENT_ID_INVALID("user", "Patient ID invalid!"),
    DOCTOR_ID_INVALID("user", "Doctor ID invalid!"),
    DOCTOR_NOT_FOUND("user", "Doctor not found!"),
    NOT_AVAILABLE_SCHEDULE("schedule", "Not found available schedule."),
    INSERT_TICKET_FAIL("schedule", "Error writing to the doctor, you may already be recorded."),
    CANCEL_TICKET_FAIL("schedule", "Failed to cancel ticket with doctor."),
    ACCESS_DENIED("user", "Access denied!"),
    BAD_REQUEST("rest", "Bad request."),
    BAD_REQUEST_S("rest", "Bad request. %s"),
    OTHER_ERROR("other", "Undefined error!");


    private String field;
    private String message;

    ServerError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    ServerError(String field, String message, String param) {
        this(field, String.format(message, param));
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
