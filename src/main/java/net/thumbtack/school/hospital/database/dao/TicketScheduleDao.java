package net.thumbtack.school.hospital.database.dao;

import net.thumbtack.school.hospital.database.model.TicketSchedule;


import java.util.List;

public interface TicketScheduleDao {
    /**
     * Get day schedule by schedule id
     *
     * @param scheduleId
     * @return
     */
    List<TicketSchedule> getDayScheduleById(int scheduleId);
}
