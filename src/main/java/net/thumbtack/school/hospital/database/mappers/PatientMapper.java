package net.thumbtack.school.hospital.database.mappers;

import java.time.LocalDate;
import java.util.List;

import net.thumbtack.school.hospital.database.model.Statistic;
import org.apache.ibatis.annotations.*;

import net.thumbtack.school.hospital.database.model.Patient;

@Mapper
public interface PatientMapper {

    @Insert("INSERT INTO `patient` ( `userId`, `email`, `address`, `phone`) "
            + "VALUES (#{user.id}, #{email}, #{address}, #{phone} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Patient patient);

    @Update({"<script>",
            "UPDATE `patient` ",
            "<set>",
            "<if test='email != null'> `email` = #{email}, </if>",
            "<if test='address != null'> `address` = #{address}, </if>",
            "<if test='phone != null'> `phone` = #{phone}, </if>",
            "</set>",
            "WHERE id = #{id};",
            "</script>"})
    void update(Patient patient);

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

    @Select("SELECT patient.id, patient.userId, firstName, lastName, patronymic, type, login, email, address, phone, token " +
            "FROM patient JOIN user ON user.id = patient.userId " +
            "LEFT JOIN session ON session.userId = user.id " +
            "WHERE patient.userId = #{id}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.session.userId", column = "userId"),
            @Result(property = "user.session.token", column = "token")
    })
    Patient getByUserId(int id);

    @Select("SELECT patient.id, patient.userId, firstName, lastName, patronymic, type, login, password, token, email, address, phone " +
            "FROM patient JOIN user ON user.id = patient.userId " +
            "JOIN session ON session.userId = user.id " +
            "WHERE token = #{token}")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.password", column = "password"),
            @Result(property = "user.session.userId", column = "userId"),
            @Result(property = "user.session.token", column = "token")
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

    @Select("SELECT type, COUNT(*) AS count " +
            "FROM ticket_schedule " +
            "JOIN schedule ON scheduleId = schedule.id " +
            "WHERE patientId = #{patientId} " +
            "AND date >= #{dateStart} " +
            "AND date <  #{dateEnd} " +
            "GROUP BY type ;")
    List<Statistic> getTicketsCount(@Param("patientId") Integer patientId, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Select("SELECT COUNT(*) FROM patient;")
    int getCount();
}
