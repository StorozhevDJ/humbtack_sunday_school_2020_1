package net.thumbtack.school.hospital.dto.request;


/*{
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество", // необязателен
"email": "адрес",
"address": "домашний адрес, одной строкой",
"phone": "номер",
"login": "логин",
"password": "пароль"
}*/

import net.thumbtack.school.hospital.dto.validation.*;

import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Email;

public class RegisterPatientDtoRequest {

    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Patronymic
    private String patronymic;
    @Email
    private String email;
    @NotBlank
    private String address;
    @Phone
    private String phone;
    @Login
    private String login;
    @Password
    private String password;

    public RegisterPatientDtoRequest() {
    }

    public RegisterPatientDtoRequest(String firstName, String lastName, String patronymic, String email, String address,
                                     String phone, String login, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.login = login;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
