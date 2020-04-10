package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Doctor;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.annotations.*;

public interface DoctorMapper {

    @Insert("INSERT INTO `doctor` (userId, specialityId, roomId) "
            + "SELECT #{user.id}, id, (SELECT id FROM room WHERE room = #{room}) AS roomId "
            + "FROM `speciality` "
            + "WHERE speciality=#{speciality}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality, room "
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
            @Result(property = "user.type.text", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Doctor getByUserId(int id);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "WHERE doctor.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type.text", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Doctor getByDoctorId(int id);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, token, speciality.speciality, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "WHERE speciality.speciality = #{speciality};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type.text", column = "type")
    })
    List<Doctor> getBySpeciality(String speciality);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality, room "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "JOIN room ON room.id = roomId "
            + "WHERE token = #{token};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type.text", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.session.token", column = "token")
    })
    Doctor getByToken(Session token);

    @Select("SELECT COUNT(*) FROM doctor;")
    int getCount();

    @Delete("DELETE FROM doctor")
    void deleteAll();

}
