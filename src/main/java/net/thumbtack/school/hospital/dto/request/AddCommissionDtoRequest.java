package net.thumbtack.school.hospital.dto.request;

/*{
"patientId": идентификатор пациента,
"doctorIds":
[
идентификатор врача1,...идентификатор врачаN
],
"room": ”номер кабинета”,
"date": "дата",
"time": "время",
"duration": время, отведенное на комиссию в минутах
}*/

import javax.validation.constraints.NotNull;

public class AddCommissionDtoRequest {

	@NotNull
	private Integer patientId;
	private Integer[] doctorIds;
	@NotNull
	private String room;
	private String date;
	private String time;
	@NotNull
	Integer duration;

	public AddCommissionDtoRequest() {
	}

	public AddCommissionDtoRequest(Integer patientId, Integer[] doctorIds, String room, String date, String time,
								   Integer duration) {
		super();
		this.patientId = patientId;
		this.doctorIds = doctorIds;
		this.room = room;
		this.date = date;
		this.time = time;
		this.duration = duration;
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

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

}
