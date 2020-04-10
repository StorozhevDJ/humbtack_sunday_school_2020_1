package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.DayScheduleDao;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DayScheduleDaoImpl extends DaoImplBase implements DayScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayScheduleDaoImpl.class);

    @Override
    public List<DaySchedule> getDayByScheduleId(int scheduleId) {
        LOGGER.debug("DAO get Day Schedule by id {}", scheduleId);
        try (SqlSession sqlSession = getSession()) {
            return getDayScheduleMapper(sqlSession).getDayByScheduleId(scheduleId);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Day Schedule by id {} {}", scheduleId, ex);
            throw ex;
        }
    }

}
