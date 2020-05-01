package net.thumbtack.school.hospital.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.ScheduleType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDtoResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;    //Administrator
    private String speciality;  //Doctor
    private String email;       //Patient
    private String address;     //Patient
    private String phone;       //Patient
    private String room;        //Doctor
    private DaySchedule daySchedule;

    public LoginDtoResponse() {}

    /**
     * Admin Login DTO response constructor
     * @param id
     * @param firstName
     * @param lastName
     * @param patronymic
     * @param position
     */
    public LoginDtoResponse(int id, String firstName, String lastName, String patronymic, String position) {
        setId(id);
        setFirstName(firstName);
        setLastName(lastName);
        setPatronymic(patronymic);
        setPosition(position);
    }

    /**
     * User Login DTO response constructor
     * @param id
     * @param firstName
     * @param lastName
     * @param patronymic
     * @param email
     * @param address
     * @param phone
     */
    public LoginDtoResponse(int id, String firstName, String lastName, String patronymic, String email,
                            String address, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Doctor Login DTO response constructor
     * @param id
     * @param firstName
     * @param lastName
     * @param patronymic
     * @param speciality
     * @param room
     */
    public LoginDtoResponse(int id, String firstName, String lastName, String patronymic, String speciality, String room) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.speciality = speciality;
        this.room = room;
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

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }




}
