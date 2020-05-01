package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.*;
import net.thumbtack.school.hospital.dto.request.RegisterDoctorDtoRequest;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;

import java.util.List;

public class DoctorMapper {

    public static LoginDtoResponse convertToDto (Doctor doctor) {
        if (doctor == null) {
            return null;
        }
        return new LoginDtoResponse(
                doctor.getId(),
                doctor.getUser().getFirstName(),
                doctor.getUser().getLastName(),
                doctor.getUser().getPatronymic(),
                doctor.getSpeciality().getName(),
                doctor.getRoom().getNumber()
        );
    }

    public static Doctor convertToEntity(RegisterDoctorDtoRequest dto) {
        if (dto == null) {
            return null;
        }
        Doctor doctor = new Doctor(
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.DOCTOR,
                        dto.getLogin(),
                        dto.getPassword(),
                        null
                ),
                new Speciality(dto.getSpeciality()),
                new Room(dto.getRoom())
        );
        return doctor;
    }


}
