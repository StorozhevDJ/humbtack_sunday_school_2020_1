package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.DayScheduleDao;
import net.thumbtack.school.hospital.database.model.DaySchedule;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class DayScheduleDaoImpl extends DaoImplBase implements DayScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PatientDaoImpl.class);

    @Override
    public int createSchedule(List<DaySchedule> daySchedule) {
        LOGGER.debug("DAO insert Schedule {}", daySchedule);
        int ret = 0;
        try (SqlSession sqlSession = getSession()) {
            try {
                getScheduleMapper(sqlSession).insert(daySchedule);
                for (DaySchedule s : daySchedule) {
                    ret += getDayScheduleMapper(sqlSession).insertDay(s.getTicketSchedule(), s.getId());
                }
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert Schedule {} {}", daySchedule, ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
        return ret;
    }

    @Override
    public List<DaySchedule> getByDoctorId(int id) {
        LOGGER.debug("DAO get Doctor Schedule by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getByDoctorId(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor Schedule by id {} {}", id, ex);
            throw ex;
        }
    }

    @Override
    public List<DaySchedule> getByDoctorSpeciality(String speciality) {
        LOGGER.debug("DAO get Doctor Schedule by speciality {}", speciality);
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getBySpeciality(speciality);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get Doctor Schedule by speciality {} {}", speciality, ex);
            throw ex;
        }
    }

    @Override
    public List<DaySchedule> getAllSchedule() {
        LOGGER.debug("DAO get All Doctor Schedule");
        try (SqlSession sqlSession = getSession()) {
            return getScheduleMapper(sqlSession).getAll();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get All Doctor Schedule {}", ex);
            throw ex;
        }
    }

    @Override
    public boolean addTicket(TicketSchedule schedule) {
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
