package net.thumbtack.school.hospital.database.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import net.thumbtack.school.hospital.database.model.Doctor;

public interface DoctorMapper {

    @Insert("INSERT INTO `doctor` ( `userId`, `position`) "
            + "VALUES ( #{user.id}, #{position}} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Doctor doctor);

    // TODO sql speciality
    @Select("SELECT doctor.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, user.tocken, doctor.position "
            + "FROM doctor JOIN user "
            + "WHERE user.token = #{token};")
    Doctor getByToken(String tocken);

    @Delete("DELETE FROM doctor")
    void deleteAll();

}
