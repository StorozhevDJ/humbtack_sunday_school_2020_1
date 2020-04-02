package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import net.thumbtack.school.hospital.database.model.Commission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.hospital.database.model.Doctor;
import net.thumbtack.school.hospital.database.model.Patient;
import net.thumbtack.school.hospital.database.model.Schedule;

public interface ScheduleMapper {

	@Insert({"<script>",
			"INSERT INTO schedule (`ticket`, `doctorId`, `patientId`, `date`, `timeStart`, `timeEnd`) VALUES",
			"<foreach item='item' collection='list' separator=','>",
			"( #{item.ticket}, #{item.doctor.id}, #{item.patient.id}, #{item.date}, #{item.timeStart}, #{item.timeEnd})",
			"</foreach>",
			"</script>"})
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(@Param("list") List<Schedule> schedule);

	@Select("SELECT schedule.id, ticket, doctorId, patientId, date, timeStart, timeEnd, room "
			+ "FROM `schedule` "
			+ "JOIN doctor ON doctor.id = doctorId "
			+ "JOIN room ON room.id = doctor.roomId "
			+ "WHERE doctor.id = #{id} "
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
							fetchType = FetchType.EAGER))
	})
	List<Schedule> getByDoctorId(int id);

	@Select("SELECT schedule.id, ticket, doctorId, patientId, date, timeStart, timeEnd, room "
			+ "FROM `schedule` "
			+ "JOIN doctor ON doctor.id = doctorId "
			+ "JOIN room ON room.id = doctor.roomId "
			+ "JOIN speciality ON speciality.id = specialityId "
			+ "WHERE speciality.speciality = #{speciality} "
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
	List<Schedule> getBySpeciality(String speciality);

	@Select("SELECT schedule.id, ticket, doctorId, patientId, date, timeStart, timeEnd, room "
			+ "FROM `schedule` "
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
	List<Schedule> getAll();

	@Update("UPDATE schedule "
			+ "SET ticket=#{ticket}, patientId=#{patient.id} "
			+ "WHERE doctorId=#{doctor.id} "
			+ "AND date = #{date} AND timeStart = #{timeStart} "
			+ "AND ticket IS NULL AND patientId IS null;")
	int insertTicketByDoctorId(Schedule ticket);
    
    /*@Update("UPDATE schedule "
    		+ "SET ticket=#{ticket}, patientId=#{patient.id} "
    		+ "WHERE doctorId=#{doctor.id} "
    		+ "AND date = #{date} AND timeStart = #{timeStart} "
    		+ "AND ticket IS NULL AND patientId IS null "
    		+ "LIMIT 1;")
    int insertTicketByDoctorSpeciality(Schedule ticket);*/

}
