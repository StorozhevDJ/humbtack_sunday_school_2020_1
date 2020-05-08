package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.database.model.User;
import net.thumbtack.school.hospital.database.model.UserType;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO `user` ( `firstName`, `lastName`, `patronymic`, type, `login`, `password`) "
            + "VALUES ( #{firstName}, #{lastName}, #{patronymic}, #{type}, #{login}, MD5(#{password}) );")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update({"<script>",
            "UPDATE `user` ",
            "<set>",
            "<if test='firstName != null'> `firstName` = #{firstName}, </if>",
            "<if test='lastName != null'> `lastName` = #{lastName}, </if>",
            "<if test='patronymic != null'> `patronymic` = #{patronymic}, </if>",
            "<if test='password != null'> `password` = MD5(#{password}) </if>",
            "</set>",
            "<where> id = #{id}</where>",
            "</script>"})
    /*@Select({"<script>",
            "SELECT * FROM trainee",
            "<where>",
            "<if test='firstName != null'> firstName like #{firstName}</if>",
            "<if test='lastName != null'> AND lastName like #{lastName}</if>",
            "<if test='rating != null'> AND rating = #{rating}",
            "</if>",
            "</where>",
            "</script>"})*/
    //List<Trainee> getAllWithParams(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rating") Integer rating);
    void update(User user);

    @Update("REPLACE into session (userId, token) VALUES (#{id}, #{session.token});")
    int insertToken(User user);

    @Delete("DELETE FROM `session` WHERE token = #{token};")
    int deleteToken(Session session);

    @Select("SELECT id, firstName, lastName, patronymic, type, login "
            + "FROM user "
            + "WHERE login = #{login} AND password = MD5(#{password});")
    @Results({
            @Result(property = "type", column = "type", javaType = UserType.class)
    })
    User getByLogin(@Param("login") String login, @Param("password") String password);

    @Select("SELECT user.id, firstName, lastName, patronymic, type, login, token "
            + "FROM user "
            + "JOIN session ON session.userId = user.id "
            + "WHERE session.token = #{token};")
    @Results({
            @Result(property = "type", column = "type", javaType = UserType.class),
            @Result(property = "session.userId", column = "user.id"),
            @Result(property = "session.token", column = "token")
    })
    User getByToken(Session token);


    @Delete("DELETE FROM user;")
    void deleteAll();

}
