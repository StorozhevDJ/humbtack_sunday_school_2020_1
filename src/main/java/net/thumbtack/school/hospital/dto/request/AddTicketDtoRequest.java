package net.thumbtack.school.hospital.dto.request;

/*{
"doctorId": идентификатор врача,
"speciality": "специальность врача",
"date": "дата",
"time": "время",
}*/

public class AddTicketDtoRequest {

    private String doctorId;
    private String speciality;
    private String date;
    private String time;

    public AddTicketDtoRequest() {
    }

    public AddTicketDtoRequest(String doctorId, String speciality, String date, String time) {
        this.doctorId = doctorId;
        this.speciality = speciality;
        this.date = date;
        this.time = time;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
