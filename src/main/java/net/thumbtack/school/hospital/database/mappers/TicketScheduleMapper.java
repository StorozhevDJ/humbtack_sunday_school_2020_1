package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.ScheduleType;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalTime;
import java.util.List;

@Mapper
public interface TicketScheduleMapper {

    // ToDo change, not correct?
    @Insert({"<script>",
            "INSERT INTO ticket_schedule (`scheduleId`, `timeStart`, `timeEnd`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{sid}, #{item.timeStart}, #{item.timeEnd})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTicketList(@Param("list") List<TicketSchedule> schedule, @Param("sid") int sid);

    @Update("UPDATE ticket_schedule " +
            "SET `ticket` = #{ticket}, `patientId` = #{patient.id}, `type` = #{scheduleType} " +
            "WHERE scheduleId = #{scheduleId} AND timeStart = #{timeStart} " +
            "AND type = 'FREE' AND ticket IS NULL AND patientId IS null;")
    int insertTicket(TicketSchedule schedule);

    @Update("UPDATE ticket_schedule " +
            "SET `ticket` = #{ticket}, `patientId` = #{patient.id}, `type` = #{scheduleType} " +
            "WHERE scheduleId = #{scheduleId} " +
            "AND ((timeStart >= #{timeStart} AND #{timeEnd} > timeStart) OR (timeEnd >= #{timeStart} AND #{timeEnd} > timeEnd)) " +
            "AND type = 'FREE' AND ticket IS NULL AND patientId IS null;")
    int insertCommissionTicket(TicketSchedule schedule);

    @Select("SELECT * " +
            "FROM `ticket_schedule` " +
            "WHERE scheduleId = #{scheduleId} " +
            "ORDER BY timeStart ASC ")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patient", column = "patientId",
                    javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
                            fetchType = FetchType.LAZY))
    })
    List<TicketSchedule> getTicketScheduleById(@Param("scheduleId") int scheduleId);

    @Select("<script>" +
            "SELECT *, type AS scheduleType " +
            "FROM `ticket_schedule` " +
            "<where> " +
            "<if test='scheduleId > 0'> scheduleId = #{scheduleId} </if> " +
            "<if test='type != null'> AND type = #{type} </if> " +
            "<if test='timeStart != null'> AND timeStart = #{timeStart} </if> " +
            "<if test='timeEnd != null'> AND timeEnd = #{timeEnd} </if> " +
            "</where> " +
            "ORDER BY timeStart ASC " +
            "</script>")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "patient", column = "patientId",
                    javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
                            fetchType = FetchType.LAZY))
    })
    List<TicketSchedule> getTicketSchedule(@Param("scheduleId") int scheduleId, @Param("type") ScheduleType type, @Param("timeStart") LocalTime timeStart, @Param("timeEnd") LocalTime timeEnd);

    @Update("UPDATE ticket_schedule "
            + "SET ticket=#{ticket}, patientId=#{patient.id} "
            + "WHERE scheduleId=#{id} "
            + "AND timeStart = #{timeStart} "
            + "AND ticket IS NULL AND patientId IS null;")
    int insertTicketByDoctorId(TicketSchedule ticket);

    @Update("UPDATE ticket_schedule " +
            "SET ticket = NULL, patientId = NULL, type = 'FREE' " +
            "WHERE ticket = #{ticket};")
    int cancelTicket(String ticket);
}

