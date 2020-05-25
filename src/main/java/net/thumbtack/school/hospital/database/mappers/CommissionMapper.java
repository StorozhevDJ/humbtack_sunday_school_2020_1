package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.Doctor;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

@Mapper
public interface CommissionMapper {

    @Insert({"INSERT INTO commission (ticket, patientId, roomId, date, timeStart, timeEnd) " +
            "VALUES (#{ticket}, #{patientId}, (SELECT id FROM room WHERE room = #{room}), #{date}, #{timeStart}, #{timeEnd})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCommission(Commission schedule);

    @Update("<script>" +
            "UPDATE ticket_schedule " +
            "SET `ticket` = #{commission.ticket}, `type` = 'COMMISSION' " +
            "WHERE id IN (<foreach item='item' collection='list' separator=','> #{item}</foreach>) " +
            "AND type = 'FREE' AND ticket IS NULL AND patientId IS NULL;" +
            "</script>")
    int insertCommissionTicket(@Param("list") List<Integer> scheduleIds, @Param("commission") Commission commission);

    @Insert("<script>" +
            "INSERT INTO commission_doctor (commissionId, doctorId) " +
            "VALUES <foreach item='item' collection='list' separator=','>(#{commissionId}, #{item.id})</foreach> " +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCommissionDoctorsId(@Param("list") List<Doctor> id, @Param("commissionId") int commissionId);

    @Select("SELECT id, scheduleId, doctorId FROM commission WHERE scheduleId = #{id};")
    List<Commission> getByScheduleId(int id);

    @Select("SELECT commission.id, ticket, patientId, timeStart, timeEnd, date, room " +
            "FROM commission " +
            "JOIN room ON roomId = room.id " +
            "WHERE patientId = #{id};")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "doctorSet", column = "id",
                    javaType = Set.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.CommissionMapper.getDoctorsIdSet",
                            fetchType = FetchType.EAGER))
    }) 
    List<Commission> getByPatientId(int id);

    @Select("SELECT doctor.id, doctor.userId, firstName, lastName, patronymic, type, speciality.name  " +
            "FROM commission_doctor " +
            "JOIN doctor ON doctorId = doctor.id " +
            "JOIN user ON userId = user.id " +
            "JOIN speciality ON specialityId = speciality.id " +
            "WHERE commissionId = #{id};")
    @Results({
            @Result(property = "speciality.name", column = "name"),
            @Result(property = "user.id", column = "userId"),
            @Result(property = "user.firstName", column = "firstName"),
            @Result(property = "user.lastName", column = "lastName"),
            @Result(property = "user.patronymic", column = "patronymic"),
            @Result(property = "user.type", column = "type")
    })
    Set<Doctor> getDoctorsIdSet(int id);

    @Update("UPDATE ticket_schedule " +
            "SET ticket = NULL, patientId = NULL, type = 'FREE' " +
            "WHERE ticket = #{ticket};")
    int cancelCommissionSchedule(@Param("ticket") String ticket);

    @Delete("DELETE FROM commission " +
            "WHERE ticket = #{ticket} AND patientId = #{patientId};")
    int deleteCommission(@Param("ticket") String ticket, @Param("patientId") int patientId);

}
