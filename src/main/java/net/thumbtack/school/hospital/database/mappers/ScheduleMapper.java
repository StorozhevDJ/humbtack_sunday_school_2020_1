package net.thumbtack.school.hospital.database.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import net.thumbtack.school.hospital.database.model.Schedule;

public interface ScheduleMapper {

	@Insert("INSERT INTO `schedule` (`doctorId`, `patientId`, `date`, `time`, `duration`, `roomId`) "
			+ "VALUES (doctor.id, patient.id, date, time, timeEnd, room);")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Schedule schedule);

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
