package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.model.UserType;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO `user` ( `firstName`, `lastName`, `patronymic`, type, `login`, `password`, `token`) "
            + "VALUES ( #{firstName}, #{lastName}, #{patronymic}, #{type.text}, #{login}, MD5(#{password}), #{session.token} );")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE `user` SET token = #{session.token} WHERE login = #{login} AND password = MD5(#{password});")
    int insertToken(User user);

    @Update("UPDATE `user` SET token = NULL WHERE token = #{token};")
    int deleteToken(Session token);

    @Select("SELECT id, firstName, lastName, patronymic, type, login, token "
            + "FROM user "
            + "WHERE login = #{login} AND password = MD5(#{password});")
    @Results({
            @Result(property = "type", column = "type", javaType = UserType.class),
            @Result(property = "session.token", column = "token")
    })
    User getByLogin(@Param("login") String login, @Param("password") String password);

    @Select("SELECT id, firstName, lastName, patronymic, type, login, token "
            + "FROM user "
            + "WHERE token = #{token};")
    @Results({
            @Result(property = "type", column = "type", javaType = UserType.class),
            @Result(property = "session.token", column = "token")
    })
    User getByToken(Session token);


    @Delete("DELETE FROM user;")
    void deleteAll();

}
