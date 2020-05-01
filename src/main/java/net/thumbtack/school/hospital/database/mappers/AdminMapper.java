package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.annotations.*;
import net.thumbtack.school.hospital.database.model.Admin;

@Mapper
public interface AdminMapper {

    @Insert("INSERT INTO `admin` ( `userId`, `position`) "
            + "VALUES (#{user.id}, #{position} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Admin admin);

    @Insert("UPDATE `admin` " +
            "SET ( `userId`, `position`) "
            + "VALUES (#{user.id}, #{position} )")
    void update(Admin admin);

    @Select("SELECT admin.id, userId, firstName, lastName, patronymic, type, login, position "
            + "FROM admin JOIN user "
            + "ON userId = user.id "
            + "WHERE user.id = #{id};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login")
    })
    Admin getByUserId(int id);

    @Select("SELECT admin.id, session.userId, firstName, lastName, patronymic, type, login, session.token, position "
            + "FROM admin JOIN user "
            + "JOIN session ON session.userId = user.id "
            + "WHERE session.token = #{token};")
    @Results({
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type"),
            @Result(property = "user.login", column = "login"),
            @Result(property = "user.session.token", column = "token")
    })
    Admin getByToken(Session token);

    @Select("SELECT COUNT(*) FROM admin;")
    int getCount();

    @Delete("DELETE FROM admin")
    void deleteAll();
}
