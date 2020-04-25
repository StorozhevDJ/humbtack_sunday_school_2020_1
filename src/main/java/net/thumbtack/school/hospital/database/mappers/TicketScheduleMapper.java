package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.TicketSchedule;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TicketScheduleMapper {

    @Insert({"<script>",
            "INSERT INTO ticket_schedule (`scheduleId`, `timeStart`, `timeEnd`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{sid}, #{item.timeStart}, #{item.timeEnd})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertTicket(@Param("list") List<TicketSchedule> schedule, @Param("sid") int sid);

    @Select("SELECT id , scheduleId, ticket, timeStart, timeEnd, patientId, type "
            + "FROM `ticket_schedule` "
            + "WHERE scheduleId = #{scheduleId} "
            + "ORDER BY timeStart ASC;")
	/*@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "patient", column = "patientId",
					javaType = List.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
							fetchType = FetchType.LAZY)),
			@Result(property = "daySchedule.timeStart", column = "timeStart"),
			@Result(property = "daySchedule.timeEnd", column = "timeEnd"),
			@Result(property = "daySchedule.type", column = "type")
	})*/
    List<TicketSchedule> getDayScheduleById(int scheduleId);

    @Update("UPDATE ticket_schedule "
            + "SET ticket=#{ticket}, patientId=#{patient.id} "
            + "WHERE scheduleId=#{id} "
            + "AND timeStart = #{timeStart} "
            + "AND ticket IS NULL AND patientId IS null;")
    int insertTicketByDoctorId(TicketSchedule ticket);

}

