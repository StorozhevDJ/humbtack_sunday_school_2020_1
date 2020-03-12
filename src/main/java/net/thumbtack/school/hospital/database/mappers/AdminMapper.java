package net.thumbtack.school.hospital.database.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import net.thumbtack.school.hospital.database.model.Admin;


public interface AdminMapper {

    @Insert("INSERT INTO `admin` ( `userId`, `position`) "
            + "VALUES (#{user.id}, #{position} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Admin admin);

    @Select("SELECT admin.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, admin.position "
            + "FROM hospital.admin JOIN hospital.user "
            + "WHERE user.id = #{id} AND admin.userId = user.id;")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Admin getByUserId(int id);

    @Select("SELECT admin.id, user.id AS userId, user.firstName, user.lastName, user.patronymic, user.type, user.login, user.token, admin.position "
            + "FROM hospital.admin JOIN hospital.user "
            + "WHERE user.token = #{token};")
    Admin getByToken(String token);

    @Delete("DELETE FROM admin")
    void deleteAll();
}
