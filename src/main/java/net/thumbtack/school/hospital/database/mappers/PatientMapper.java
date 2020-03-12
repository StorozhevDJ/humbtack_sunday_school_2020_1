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

    @Select("SELECT * FROM `patient` WHERE id = #{id}")
    Patient getById(int id);

    @Select("SELECT patient.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, user.tocken, patient.email, patient.address, patient.phone "
            + "FROM `patient` JOIN `user` "
            + "WHERE user.token = #{token};")
    Patient getByToken(String tocken);

    @Delete("DELETE FROM `patient`")
    void deleteAll();
}
