package net.thumbtack.school.hospital.serverexception;

public class ServerException extends Exception {

    private static final long serialVersionUID = -597432281485726720L;

    private final ServerError error;

    public ServerException(ServerError error) {
        this.error = error;
    }

    public ServerError getError() {
        return error;
    }
}
