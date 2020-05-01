package net.thumbtack.school.hospital.dto.response;

/*{
"ticket": “номер талона на комиссию”,
"patientId": идентификатор пациента,
"doctorIds":
[
идентификатор врача1,...идентификатор врачаN
]
"room": ”номер кабинета”,
"date": "дата",
"time": "время начала",
"duration": время, отведенное на комиссию в минутах
}*/

public class AddCommissionDtoResponse {

    private String ticket;
    private String patientId;
    private int doctorIds;
    private String room;
    private String date;
    private String time;
    private String duration;

    public AddCommissionDtoResponse(String ticket, String patientId, int doctorIds, String room, String date,
                                    String time, String duration) {
        super();
        this.ticket = ticket;
        this.patientId = patientId;
        this.doctorIds = doctorIds;
        this.room = room;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(int doctorIds) {
        this.doctorIds = doctorIds;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }


}
