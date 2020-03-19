package net.thumbtack.school.hospital.database.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import net.thumbtack.school.hospital.database.model.Patient;

public interface PatientMapper {

    @Insert("INSERT INTO `patient` ( `userId`, `email`, `address`, `phone`) "
            + "VALUES (#{user.id}, #{email}, #{address}, #{phone} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Patient patient);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, 'type', login, token, email, address, phone " +
            "FROM patient JOIN user ON user.id = patient.userId" +
            "WHERE patient.id = #{id}")
    Patient getById(int id);

    @Select("SELECT patient.id, userId, firstName, lastName, patronymic, 'type', login, token, email, address, phone " +
            "FROM patient JOIN user ON user.id = patient.userId" +
            "WHERE token = #{token}")
    Patient getByToken(String token);

    @Delete("DELETE FROM `patient`")
    void deleteAll();
}
