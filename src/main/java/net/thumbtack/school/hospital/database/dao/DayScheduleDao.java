package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.DaySchedule;


import java.util.List;

public interface DayScheduleDao {
    /**
     * Get day schedule by schedule id
     *
     * @param scheduleId
     * @return
     */
    List<DaySchedule> getDayByScheduleId(int scheduleId);
}
