package net.thumbtack.school.hospital.serverexception;

public enum ServerError {

    LOGIN_ALREADY_EXISTS("login", "User %s already exists!"),
    LOGIN_OR_PASSWORD_INVALID("login", "User or password is incorrect!"),
    TOKEN_INVALID("login", "Token invalid!");


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
