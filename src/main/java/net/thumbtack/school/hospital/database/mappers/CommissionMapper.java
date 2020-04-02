package net.thumbtack.school.hospital.database.mappers;

import net.thumbtack.school.hospital.database.model.Commission;
import net.thumbtack.school.hospital.database.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommissionMapper {

    @Insert({"<script>",
            "INSERT INTO commission (`scheduleId`, `doctorId`) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.scheduleId}, #{item.doctorId})",
            "</foreach>",
            "</script>"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCommission(@Param("list") List<Commission> schedule);

    @Select("SELECT id, scheduleId, doctorId FROM commission WHERE scheduleId = #{id};")
    List<Commission> getByScheduleId(int id);
}
