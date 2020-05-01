package net.thumbtack.school.hospital.mapper;

import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.model.UserType;
import net.thumbtack.school.hospital.dto.request.EditAdminDtoRequest;
import net.thumbtack.school.hospital.dto.request.RegisterAdminDtoRequest;
import net.thumbtack.school.hospital.dto.response.LoginDtoResponse;

public  class AdminMapper {

    public static Admin convertToEntity(LoginDtoResponse dto) {
        if (dto == null) {
            return null;
        }
        return new Admin(
                dto.getId(),
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.ADMINISTRATOR,
                        null,
                        null,
                        new Session(dto.getId(), null)
                ),
                dto.getPosition()
        );
    }

    public static Admin convertToEntity(RegisterAdminDtoRequest dto) {
        if (dto == null) {
            return null;
        }
        return new Admin(
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.ADMINISTRATOR,
                        dto.getLogin(),
                        dto.getPassword(),
                        null
                ),
                dto.getPosition()
        );
    }


    public static Admin convertToEntity(RegisterAdminDtoRequest dto, Session session) {
        if (dto == null) {
            return null;
        }
        return new Admin(
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.ADMINISTRATOR,
                        dto.getLogin(),
                        dto.getPassword(),
                        session
                ),
                dto.getPosition()
        );

    }

    public static Admin convertToEntity(EditAdminDtoRequest dto, String login) {
        if (dto == null) {
            return null;
        }
        return new Admin(
                new User(
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getPatronymic(),
                        UserType.ADMINISTRATOR,
                        login,
                        dto.getNewPassword(),
                        null
                ),
                dto.getPosition()
        );
    }

    public static LoginDtoResponse convertToDto (Admin admin) {
        if (admin == null) {
            return null;
        }
        return new LoginDtoResponse(
                admin.getId(),
                admin.getUser().getFirstName(),
                admin.getUser().getLastName(),
                admin.getUser().getPatronymic(),
                admin.getPosition()
        );
    }

}
