package net.thumbtack.school.hospital.dto.response;

/*
{
"id": "идентификационный номер",
"firstName": "имя",
"lastName": "фамилия",
"patronymic": "отчество",
"position": "должность"
}
 */

public class AddAdminDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;

    public AddAdminDtoResponse(int id, String firstName, String lastName, String patronymic, String position) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setPosition(position);
    }

    public AddAdminDtoResponse(String firstName, String lastName, String patronymic, String position) {
        this(0, firstName, lastName, patronymic, position);
    }

    public AddAdminDtoResponse() {
        this(new String(), new String(), new String(), new String());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
