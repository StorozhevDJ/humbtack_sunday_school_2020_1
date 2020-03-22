package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import net.thumbtack.school.hospital.database.model.Patient;

public interface PatientMapper {

    @Insert("INSERT INTO `patient` ( `userId`, `email`, `address`, `phone`) "
            + "VALUES (#{user.id}, #{email}, #{address}, #{phone} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Patient patient);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, type, login, email, address, phone " +
            "FROM patient JOIN user ON user.id = userId " +
            "WHERE patient.id = #{id}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Patient getByPatientId(int id);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, type, login, email, address, phone " +
            "FROM patient JOIN user ON user.id = userId " +
            "WHERE userId = #{id}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Patient getByUserId(int id);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, type, login, token, email, address, phone " +
            "FROM patient JOIN user ON user.id = userId " +
            "WHERE token = #{token}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.token", column = "token")
    })
    Patient getByToken(String token);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, type, login, email, address, phone " +
            "FROM patient JOIN user ON user.id = patient.userId " +
            "WHERE doctor.id = #{id}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    List<Patient> getByDoctorId(int id);

    @Select("SELECT COUNT(*) FROM patient;")
    int getCount();

    @Delete("DELETE FROM `patient`")
    void deleteAll();
}
