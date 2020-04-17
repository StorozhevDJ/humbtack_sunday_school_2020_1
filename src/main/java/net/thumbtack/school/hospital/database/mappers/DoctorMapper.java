package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Doctor;
import java.util.List;
import net.thumbtack.school.hospital.database.model.Session;
import org.apache.ibatis.annotations.*;

public interface DoctorMapper {

    @Insert("INSERT INTO `doctor` (userId, specialityId, roomId) "
            + "SELECT #{user.id}, id, (SELECT id FROM room WHERE room = #{room.number}) AS roomId "
            + "FROM `speciality` "
            + "WHERE name=#{speciality.name}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, name, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "WHERE user.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "speciality.name", column = "name"),
            @Result(property = "room.number", column = "room")
    })
    Doctor getByUserId(int id);

    @Select("SELECT doctor.id, doctor.userId, firstName, lastName, patronymic, type, login, token, speciality.name, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "LEFT JOIN session ON session.userId = user.id "
            + "WHERE doctor.id = #{id};")
    @Results({
            @Result(property = "room.number", column = "room"),
            @Result(property = "speciality.name", column = "name"),
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Doctor getByDoctorId(int id);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, speciality.name, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "WHERE speciality.name = #{name};")
    @Results({
            @Result(property = "room.number", column = "room"),
            @Result(property = "speciality.name", column = "name"),
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type")
    })
    List<Doctor> getBySpeciality(String name);

    @Select("SELECT doctor.id, session.userId, firstName, lastName, patronymic, type, login, session.token, speciality.name, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "JOIN session ON session.userId = user.id "
            + "WHERE session.token = #{token};")
    @Results({
            @Result(property = "room.number", column = "room"),
            @Result(property = "speciality.name", column = "name"),
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.session.token", column = "token")
    })
    Doctor getByToken(Session token);

    @Select("SELECT COUNT(*) FROM doctor;")
    int getCount();

    @Delete("DELETE FROM doctor")
    void deleteAll();

}
