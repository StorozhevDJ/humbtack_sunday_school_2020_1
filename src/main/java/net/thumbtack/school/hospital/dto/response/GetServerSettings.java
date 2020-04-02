package net.thumbtack.school.hospital.dto.response;

/*{
"maxNameLength": значение max_name_length,
"minPasswordLength": значение min_password_length
}*/

public class GetServerSettings {

    private String maxNameLength;
    private String minPasswordLength;

    public GetServerSettings(String maxNameLength, String minPasswordLength) {
        super();
        this.maxNameLength = maxNameLength;
        this.minPasswordLength = minPasswordLength;
    }

    public String getMaxNameLength() {
        return maxNameLength;
    }

    public void setMaxNameLength(String maxNameLength) {
        this.maxNameLength = maxNameLength;
    }

    public String getMinPasswordLength() {
        return minPasswordLength;
    }

    public void setMinPasswordLength(String minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }


}
