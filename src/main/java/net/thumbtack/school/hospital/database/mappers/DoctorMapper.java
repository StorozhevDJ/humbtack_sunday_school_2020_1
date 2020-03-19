package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Doctor;

import java.util.List;

import org.apache.ibatis.annotations.*;

public interface DoctorMapper {

    @Insert("INSERT INTO `doctor` (userId, specialityId) "
            + "SELECT #{user.id}, id "
            + "FROM `speciality` "
            + "WHERE speciality=#{speciality}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Doctor doctor);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "WHERE user.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            //@Result(property = "speciality.id", column = "speciality.id"),
            @Result(property = "speciality", column = "speciality.speciality")
    })
    Doctor getByUserId(int id);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "WHERE doctor.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            //       @Result(property = "speciality.id", column = "speciality.id"),
            @Result(property = "speciality", column = "speciality.speciality")
    })
    Doctor getByDoctorId(int id);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, token, speciality.speciality "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "WHERE speciality.speciality = #{speciality};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "speciality.id", column = "speciality.id"),
            @Result(property = "speciality", column = "speciality.speciality")
    })
    List<Doctor> getBySpeciality(String speciality);

    @Select("SELECT doctor.id, userId, firstName, lastName, patronymic, type, login, token, speciality.speciality "
            + "FROM doctor JOIN user "
            + "ON userId = user.id "
            + "JOIN speciality ON specialityId = speciality.id "
            + "WHERE token = #{token};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            //@Result(property = "speciality.id", column = "speciality.id"),
            @Result(property = "speciality", column = "speciality.speciality")
    })
    Doctor getByToken(String tocken);

    @Select("SELECT COUNT(*) FROM doctor;")
    int getCount();

    @Delete("DELETE FROM doctor")
    void deleteAll();

}
