package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommissionMapper {

    @Insert({"INSERT INTO commission (ticket, patientId, room, date, timeStart, timeEnd) " +
            "VALUES (#{ticket}, #{patientId}, (SELECT id FROM room WHERE room = #{room}), #{date}, #{timeStart}, #{timeEnd})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCommission(Commission schedule);

    @Select("SELECT id, scheduleId, doctorId FROM commission WHERE scheduleId = #{id};")
    List<Commission> getByScheduleId(int id);
}
