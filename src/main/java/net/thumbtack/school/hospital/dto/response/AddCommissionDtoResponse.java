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
    private Integer patientId;
    private Integer[] doctorIds;
    private String room;
    private String date;
    private String time;
    private int duration;

    public AddCommissionDtoResponse(String ticket, Integer patientId, Integer[] doctorIds, String room, String date,
                                    String time, int duration) {
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer[] getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(Integer[] doctorIds) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


}
