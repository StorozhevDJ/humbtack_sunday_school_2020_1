package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.TicketScheduleDao;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TicketScheduleDaoImpl extends DaoImplBase implements TicketScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketScheduleDaoImpl.class);

    @Override
    public List<TicketSchedule> getDayScheduleById(int scheduleId) {
        LOGGER.debug("DAO get Day Schedule by id {}", scheduleId);
        try (SqlSession sqlSession = getSession()) {
            return getDayScheduleMapper(sqlSession).getDayScheduleById(scheduleId);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Day Schedule by id {} {}", scheduleId, ex);
            throw ex;
        }
    }

}
