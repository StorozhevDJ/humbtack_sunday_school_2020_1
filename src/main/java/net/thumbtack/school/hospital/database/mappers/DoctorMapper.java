package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Doctor;
import org.apache.ibatis.annotations.*;

public interface DoctorMapper {

    @Insert("INSERT INTO `doctor` ( `userId`, `position`) "
            + "VALUES ( #{user.id}, #{position}} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Doctor doctor);

    @Select("SELECT doctor.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, doctor.position "
            + "FROM hospital.doctor JOIN hospital.user "
            + "ON doctor.userId = user.id "
            + "JOIN speciality ON doctor.speciality = speciality.id"
            + "WHERE user.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Doctor getByUserId(int id);

    @Select("SELECT doctor.id, doctor.userid, firstName, lastName, patronymic, 'type', login, token, speciality.speciality "
            + "FROM doctor "
            + "JOIN user ON user.id = doctor.userId"
            + "JOIN speciality ON doctor.speciality = speciality.id"
            + "WHERE doctor.id = #{id};")
    Doctor getByDoctorId(int id);

    @Select("SELECT doctor.id, doctor.userid, firstName, lastName, patronymic, 'type', token, speciality.speciality "
            + "FROM doctor "
            + "JOIN user ON user.id = doctor.userId"
            + "JOIN speciality ON doctor.speciality = speciality.id"
            + "WHERE speciality.speciality = #{speciality};")
    Doctor getBySpeciality(String speciality);

    // TODO sql speciality
    @Select("SELECT doctor.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, user.tocken, doctor.position "
            + "FROM doctor JOIN user "
            + "WHERE user.token = #{token};")
    Doctor getByToken(String tocken);

    @Delete("DELETE FROM doctor")
    void deleteAll();

}
