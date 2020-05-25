package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.dto.request.EditPatientDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterPatientDtoRequest;
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


    public static Patient convertToEntity(RegisterPatientDtoRequest dtoRequest) {
        return new Patient(
                new User(
                        dtoRequest.getFirstName(),
                        dtoRequest.getLastName(),
                        dtoRequest.getPatronymic(),
                        UserType.PATIENT,
                        dtoRequest.getLogin(),
                        dtoRequest.getPassword(),
                        null
                ),
                dtoRequest.getEmail(),
                dtoRequest.getAddress(),
                dtoRequest.getPhone().replaceAll("[^+0-9]", "")
        );
    }

    public static Patient convertToEntity(EditPatientDtoRequest dtoRequest) {
        return new Patient(
                new User(
                        dtoRequest.getFirstName(),
                        dtoRequest.getLastName(),
                        dtoRequest.getPatronymic(),
                        UserType.PATIENT,
                        null,
                        dtoRequest.getNewPassword(),
                        null
                ),
                dtoRequest.getEmail(),
                dtoRequest.getAddress(),
                dtoRequest.getPhone()
        );
    }

}
