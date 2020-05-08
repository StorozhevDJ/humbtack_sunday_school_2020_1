package net.thumbtack.school.hospital.dto.request;

public class DeleteDoctorDtoRequest {

    private String date;

    public DeleteDoctorDtoRequest(String date) {
        this.date = date;
    }

    public DeleteDoctorDtoRequest() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
