package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.hospital.database.model.Schedule;

public interface ScheduleMapper {

    /*@Insert("INSERT INTO `schedule` (`doctorId`, `patientId`, `date`, `time`, `timeEnd`, `roomId`) "
            + "VALUES (doctor.id, patient.id, date, time, timeEnd, room);")*/
    @Insert({"<script>",
            "INSERT INTO schedule (`doctorId`, `patientId`, `date`, `time`, `timeEnd`, `roomId`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.id}, #{item. id}, #{item.date}, #{item.time}, #{item.timeEnd}, #{item.room.id} )",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("list") List<Schedule> schedule);

    @Select("SELECT *, `schedule`.id, doctorId, patientId, `date`, `time` , duration, room "
            + "FROM `schedule` JOIN doctor ON doctor.id = doctorId")
    @Results({@Result(property = "id", column = "id"),
            @Result(property = "patient", column = "id",
                    javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getById",
                            fetchType = FetchType.LAZY))})
	Schedule getByDoctorId(int id);

	// TODO
}
