package net.thumbtack.school.hospital.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticDtoResponse {
    private Integer patientId;
    private Integer doctorId;
    private int freeTicketsCount;
    private int receptionTicketsCount;
    private int commissionTicketsCount;

    public StatisticDtoResponse() {
    }

    public StatisticDtoResponse(Integer patientId, Integer doctorId, int freeTicketsCount, int receptionTicketsCount, int commissionTicketsCount) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.freeTicketsCount = freeTicketsCount;
        this.receptionTicketsCount = receptionTicketsCount;
        this.commissionTicketsCount = commissionTicketsCount;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorsCount) {
        this.doctorId = doctorsCount;
    }

    public int getFreeTicketsCount() {
        return freeTicketsCount;
    }

    public void setFreeTicketsCount(int freeTicketsCount) {
        this.freeTicketsCount = freeTicketsCount;
    }

    public int getReceptionTicketsCount() {
        return receptionTicketsCount;
    }

    public void setReceptionTicketsCount(int receptionTicketsCount) {
        this.receptionTicketsCount = receptionTicketsCount;
    }

    public int getCommissionTicketsCount() {
        return commissionTicketsCount;
    }

    public void setCommissionTicketsCount(int commissionTicketsCount) {
        this.commissionTicketsCount = commissionTicketsCount;
    }
}
