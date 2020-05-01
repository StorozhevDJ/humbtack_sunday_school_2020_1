package net.thumbtack.school.hospital.dto.request;

/*{
"login": "логин",
"password": "пароль"
}*/

import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.dto.validation.Password;

public class LoginDtoRequest {

    @Login
    private String login;
    @Password
    private String password;

    public LoginDtoRequest() {}

    public LoginDtoRequest(String login, String password) {
        //super();
        this.login = login;
        this.password = password;
    }


    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


}
