package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import net.thumbtack.school.hospital.database.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface DayScheduleMapper {

	@Insert({"<script>",
			"INSERT INTO schedule (`doctorId`, `date`) VALUES",
			"<foreach item='item' collection='list' separator=','>",
			"( #{item.doctor.id}, #{item.date})",
			"</foreach>",
			"</script>"})
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(@Param("list") List<DaySchedule> daySchedule);

	@Select("SELECT schedule.id, doctorId, date "
			+ "FROM `schedule` "
			+ "JOIN doctor ON doctor.id = doctorId "
			+ "JOIN room ON room.id = doctor.roomId "
			+ "WHERE doctor.id = #{id} "
			+ "AND date >= CURDATE() AND date < CURDATE() + INTERVAL 2 MONTH "
			+ "ORDER BY date ASC;")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "doctor", column = "doctorId",
					javaType = Doctor.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.DoctorMapper.getByDoctorId",
							fetchType = FetchType.LAZY)),
			/*@Result(property = "daySchedule", column = "id",
					javaType = List.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.ScheduleMapper.getDayByScheduleId",
							fetchType = FetchType.EAGER))*//*,
			@Result(property = "daySchedule.id", column = "id"),
			@Result(property = "daySchedule.ticket", column = "ticket"),
			@Result(property = "daySchedule.patient", column = "patientId",
					javaType = Patient.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
							fetchType = FetchType.LAZY)),
			@Result(property = "daySchedule.timeStart", column = "timeStart"),
			@Result(property = "daySchedule.timeEnd", column = "timeEnd"),
			@Result(property = "daySchedule.type", column = "type")*/
	})
	List<DaySchedule> getByDoctorId(int id);

	@Select("SELECT schedule.id, ticket, doctorId, scheduleId, patientId, date, timeStart, timeEnd, room "
            + "FROM `schedule` "
            + "JOIN ticket_schedule ON scheduleId = schedule.id "
            + "JOIN doctor ON doctor.id = doctorId "
            + "JOIN room ON room.id = doctor.roomId "
            + "JOIN speciality ON speciality.id = specialityId "
            + "WHERE speciality.name = #{name} "
            + "AND date >= CURDATE() AND date < CURDATE() + INTERVAL 2 MONTH "
            + "ORDER BY date ASC, timeStart ASC;")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "doctor", column = "doctorId",
					javaType = Doctor.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.DoctorMapper.getByDoctorId",
                            fetchType = FetchType.LAZY)),
            @Result(property = "patient", column = "patientId",
                    javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
                            fetchType = FetchType.LAZY)),
            @Result(property = "commission", column = "id",
                    javaType = List.class,
                    one = @One(select = "net.thumbtack.school.hospital.database.mappers.CommissionMapper.getByScheduleId",
                            fetchType = FetchType.LAZY))
    })
    List<DaySchedule> getBySpeciality(String name);

	@Select("SELECT schedule.id, ticket, doctorId, scheduleId, patientId, date, timeStart, timeEnd, room "
			+ "FROM `schedule` "
			+ "JOIN ticket_schedule ON scheduleId = schedule.id "
			+ "JOIN doctor ON doctor.id = doctorId "
			+ "JOIN room ON room.id = doctor.roomId "
			+ "JOIN speciality ON speciality.id = specialityId "
			+ "AND date >= CURDATE() AND date < CURDATE() + INTERVAL 2 MONTH "
			+ "ORDER BY date ASC, timeStart ASC;")
	@Results({
			@Result(property = "id", column = "id"),
			@Result(property = "doctor", column = "doctorId",
					javaType = Doctor.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.DoctorMapper.getByDoctorId",
							fetchType = FetchType.LAZY)),
			@Result(property = "patient", column = "patientId",
					javaType = Patient.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.PatientMapper.getByPatientId",
							fetchType = FetchType.LAZY)),
			@Result(property = "commission", column = "id",
					javaType = List.class,
					one = @One(select = "net.thumbtack.school.hospital.database.mappers.CommissionMapper.getByScheduleId",
							fetchType = FetchType.LAZY))
	})
	List<DaySchedule> getAll();


    
    /*@Update("UPDATE schedule "
    		+ "SET ticket=#{ticket}, patientId=#{patient.id} "
    		+ "WHERE doctorId=#{doctor.id} "
    		+ "AND date = #{date} AND timeStart = #{timeStart} "
    		+ "AND ticket IS NULL AND patientId IS null "
    		+ "LIMIT 1;")
    int insertTicketByDoctorSpeciality(Schedule ticket);*/

}
