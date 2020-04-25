package net.thumbtack.school.hospital.dto.request;

import net.thumbtack.school.hospital.dto.validation.Login;
import net.thumbtack.school.hospital.dto.validation.Password;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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


    @NotBlank(message = "Имя админстратора не задано. ")
    @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ\\w-]{1,50}$", message = "Имя администратора может содержать только только русские буквы, пробелы и знак “минус” (используемый как тире) и быть длинной от 1 до 50 символов. ")
    private String firstName;
    @NotBlank(message = "Фамилия админстратора не задана. ")
    @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ\\w-]{1,50}$", message = "Фамилия администратора может содержать только только русские буквы, пробелы и знак “минус” (используемый как тире) и быть длинной от 1 до 50 символов. ")
    private String lastName;
    @Pattern(regexp = "^[А-ЯЁ][а-яА-ЯёЁ\\w-]{1,50}$", message = "Отчество администратора может содержать только только русские буквы, пробелы и знак “минус” (используемый как тире) и быть длинной от 1 до 50 символов. ")
    private String patronymic;
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
        this(new String(), new String(), new String(), new String(), new String(), new String());
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
