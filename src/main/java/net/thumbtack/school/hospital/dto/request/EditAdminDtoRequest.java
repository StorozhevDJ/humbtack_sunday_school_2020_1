package net.thumbtack.school.hospital.dto.request;

/*{
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество",
"position": "должность",
"oldPassword": "прежний пароль",
"newPassword": "новый пароль"
}*/

import net.thumbtack.school.hospital.dto.validation.LastName;
import net.thumbtack.school.hospital.dto.validation.Password;
import net.thumbtack.school.hospital.dto.validation.FirstName;
import net.thumbtack.school.hospital.dto.validation.Patronymic;

import javax.validation.constraints.NotNull;

public class EditAdminDtoRequest {

    @FirstName
    private String firstName;
    @LastName
    private String lastName;
    @Patronymic
    private String patronymic;
    @NotNull
    private String position;
    private String oldPassword;
    @Password
    private String newPassword;


    public EditAdminDtoRequest() {
    }

    public EditAdminDtoRequest(String firstName, String lastName, String patronymic, String position,
                               String oldPassword, String newPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.position = position;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
