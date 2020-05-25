package net.thumbtack.school.hospital.serverexception;

public enum ServerError {

    LOGIN_ALREADY_EXISTS("login", "User login already exists!"),
    LOGIN_OR_PASSWORD_INVALID("login", "User Login or password is incorrect!"),
    LOGIN_EMPTY("login", "Login is empty!"),
    LOGIN_LONG("login", "Login is too long!"),
    LOGIN_INAPPROPRIATE("login", "Login  is inappropriate!"),
    PHONE_INVALID("login", "Phone number is invalid!"),
    NAME_INVALID("login", "Fisrtname or Lastname is invalid!"),
    NAME_LONG("login", "Fisrtname or Lastname is to long!"),
    NAME_EMPTY("login", "Fisrtname or Lastname is empty!"),
    TOKEN_INVALID("login", "Token invalid!"),
    PASSWORD_EMPTY("login", "Password is empty!"),
    PASSWORD_SHORT("login", "Password is too short!"),
    EMAIL_INVALID("login", "Email is invalid!"),
    USER_ID_INVALID("user", "User ID invalid!"),
    PATIENT_ID_INVALID("user", "Patient ID invalid!"),
    PATIENT_NOT_FOUND("user", "Patient not found!"),
    DOCTOR_ID_INVALID("user", "Doctor ID invalid!"),
    DOCTOR_NOT_FOUND("user", "Doctor not found!"),
    ACCESS_DENIED("user", "Access denied!"),
    NOT_AVAILABLE_SCHEDULE("schedule", "Not found available schedule."),
    INSERT_SCHEDULE_FAIL("schedule", "Failed to insert schedule."),
    INSERT_TICKET_FAIL("schedule", "Error writing to the doctor, you may already be recorded or time used other patient."),
    CANCEL_TICKET_FAIL("schedule", "Failed to cancel ticket."),
    BAD_REQUEST("rest", "Bad request."),
    BAD_REQUEST_S("rest", "Bad request. %s"),
    BAD_TIME("rest", "Bad time in request."),
    BAD_DATE("rest", "Bad date in request."),
    OTHER_ERROR("other", "Undefined error!");


    private final String field;
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
