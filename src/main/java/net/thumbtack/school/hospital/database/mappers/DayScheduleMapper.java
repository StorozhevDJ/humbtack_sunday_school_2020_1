package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.DaySchedule;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DayScheduleMapper {

    @Insert({"<script>",
            "INSERT INTO day_schedule (`scheduleId`, `timeStart`, `timeEnd`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{sid}, #{item.timeStart}, #{item.timeEnd})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDay(@Param("list") List<DaySchedule> schedule, @Param("sid") int sid);

    @Select("SELECT id , scheduleId, ticket, timeStart, timeEnd, patientId, type "
            + "FROM `day_schedule` "
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
    List<DaySchedule> getDayByScheduleId(int scheduleId);

}
