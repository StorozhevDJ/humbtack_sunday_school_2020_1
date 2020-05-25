package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.*;

import javax.validation.constraints.NotNull;

/*
{
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество", // необязателен
"position": "должность",
"login": "логин",
"password": "пароль"
}
 */

public class RegisterAdminDtoRequest {


    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Patronymic
    private String patronymic;
    @NotNull
    private String position;
    @Login
    private String login;
    @Password
    private String password;


    public RegisterAdminDtoRequest(String firstName, String lastName, String patronymic, String position, String login, String password) {
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setPosition(position);
        setLogin(login);
        setPassword(password);
    }

    public RegisterAdminDtoRequest() {

    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
