package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.hospital.database.model.Schedule;

public interface ScheduleMapper {

    /*@Insert("INSERT INTO `schedule` (`doctorId`, `patientId`, `date`, `time`, `timeEnd`, `roomId`) "
            + "VALUES (doctor.id, patient.id, date, time, timeEnd, room);")*/
	/*
	 INSERT INTO `schedule` (`ticket`, `doctorId`, `patientId`, `date`, `time`, `timeEnd`, `roomId`)
		VALUES ('ticket', 1, null, CURDATE(), CURTIME(), CURTIME(), 1); 
	 */
    @Insert({"<script>",
            "INSERT INTO schedule (`ticket`, `doctorId`, `patientId`, `date`, `time`, `timeEnd`, `roomId`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.ticket}, #{item.doctorId}, #{item.patientId}, #{item.date}, #{item.time}, #{item.timeEnd}, (SELECT id FROM room WHERE room=#{item.room}) )",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("list") List<Schedule> schedule);

    @Select("SELECT schedule.id, ticket, doctorId, patientId, date, time , timeEnd, room "
            + "FROM `schedule` "
            + "JOIN doctor ON doctor.id = doctorId "
            + "JOIN room ON room.id = schedule.roomId "
            + "WHERE doctor.id = #{id} "
            + "ORDER BY date ASC, time ASC;")
    /*@Results({@Result(property = "id", column = "id"),
            @Result(property = "patient", column = "id",
                    javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getById",
                            fetchType = FetchType.LAZY))})*/
    List<Schedule> getByDoctorId(int id);

    @Select("SELECT schedule.id, ticket, doctorId, patientId, date, time , timeEnd, room "
            + "FROM `schedule` "
            + "JOIN doctor ON doctor.id = doctorId "
            + "JOIN room ON room.id = schedule.roomId "
            + "JOIN speciality ON speciality.id = specialityId "
            + "WHERE speciality.speciality = #{speciality} "
            + "ORDER BY date ASC, time ASC;")
    /*@Results({@Result(property = "id", column = "id"),
            @Result(property = "patient", column = "id",
                    javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getById",
                            fetchType = FetchType.LAZY))})*/
    List<Schedule> getBySpeciality(String speciality);

}
