package net.thumbtack.school.hospital.dto.response.getticket;


import com.fasterxml.jackson.annotation.JsonValue;

public class GetTicketCommissionDto {
    private DoctorsCommission doctorsCommission;

    public GetTicketCommissionDto(DoctorsCommission doctorsCommission) {
        this.doctorsCommission = doctorsCommission;
    }

    @JsonValue
    public DoctorsCommission getDoctorsCommission() {
        return doctorsCommission;
    }

    public void setDoctorsCommission(DoctorsCommission doctorsCommission) {
        this.doctorsCommission = doctorsCommission;
    }
}
