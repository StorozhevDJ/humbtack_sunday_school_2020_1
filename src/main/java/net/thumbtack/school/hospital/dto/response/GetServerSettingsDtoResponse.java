package net.thumbtack.school.hospital.dto.response;

/*{
"maxNameLength": значение max_name_length,
"minPasswordLength": значение min_password_length
}*/

public class GetServerSettingsDtoResponse {

    private int maxNameLength;
    private int minPasswordLength;

    public GetServerSettingsDtoResponse() {
    }

    public GetServerSettingsDtoResponse(int maxNameLength, int minPasswordLength) {
        super();
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
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

}
