package net.thumbtack.school.hospital.database.daoimpl;

import java.util.List;

import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.model.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleDaoImpl extends DaoImplBase implements ScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    @Override
    public int createSchedule(List<Schedule> schedule) {
        LOGGER.debug("DAO insert Schedule {}", schedule);
        int ret = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                ret = getScheduleMapper(sqlSession).insert(schedule);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Schedule {} {}", schedule, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return ret;
    }

    @Override
    public List<Schedule> getAllShedule() {
        return null;
    }

}
