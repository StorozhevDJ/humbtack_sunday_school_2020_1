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

import net.thumbtack.school.hospital.dto.validation.Date;
import net.thumbtack.school.hospital.dto.validation.Time;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddCommissionDtoRequest {

	@NotNull
	@Positive
	private Integer patientId;
	private Integer[] doctorIds;
	@NotNull
	private String room;
	@Date
	private String date;
	@Time
	private String time;
	@NotNull
	@Positive
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
