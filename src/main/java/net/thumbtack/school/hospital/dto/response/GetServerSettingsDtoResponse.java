package net.thumbtack.school.hospital.dto.response;

/*{
"maxNameLength": значение max_name_length,
"minPasswordLength": значение min_password_length
}*/

public class GetServerSettingsDtoResponse {

    private int maxNameLength;
    private int minPasswordLength;
    private int serverPort;
    private String cookie;

    public GetServerSettingsDtoResponse() {
    }

    public GetServerSettingsDtoResponse(int maxNameLength, int minPasswordLength, int serverPort) {
        super();
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
        this.serverPort = serverPort;
    }

    public int getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public int getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
