package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;

public class PatientMapper {

    public static LoginDtoResponse convertToDto (Patient patient) {
        if (patient == null) {
            return null;
        }
        return new LoginDtoResponse(
                patient.getId(),
                patient.getUser().getFirstName(),
                patient.getUser().getLastName(),
                patient.getUser().getPatronymic(),
                patient.getEmail(),
                patient.getAddress(),
                patient.getPhone()
        );
    }

}
