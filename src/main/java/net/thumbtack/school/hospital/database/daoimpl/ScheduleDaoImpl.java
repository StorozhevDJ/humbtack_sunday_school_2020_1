package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.ScheduleDao;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.Schedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ScheduleDaoImpl extends DaoImplBase implements ScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    @Override
    public int createSchedule(List<Schedule> schedule) {
        LOGGER.debug("DAO insert Schedule {}", schedule);
        int ret = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).insert(schedule);
                for (Schedule s : schedule) {
                    ret += getDayScheduleMapper(sqlSession).insertDay(s.getDaySchedule(), s.getId());
                }
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
    public List<Schedule> getByDoctorId(int id) {
        LOGGER.debug("DAO get Doctor Schedule by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getByDoctorId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor Schedule by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public List<Schedule> getByDoctorSpeciality(String speciality) {
        LOGGER.debug("DAO get Doctor Schedule by speciality {}", speciality);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getBySpeciality(speciality);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor Schedule by speciality {} {}", speciality, ex);
            throw ex;
        }
    }

    @Override
    public List<Schedule> getAllSchedule() {
        LOGGER.debug("DAO get All Doctor Schedule");
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Doctor Schedule {}", ex);
            throw ex;
        }
    }

    @Override
    public boolean addTicket(DaySchedule schedule) {
        LOGGER.debug("DAO insert Ticket {}", schedule);
        int ret = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                ret = getScheduleMapper(sqlSession).insertTicketByDoctorId(schedule);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Ticket {} {}", schedule, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return ret == 1;
    }


}
