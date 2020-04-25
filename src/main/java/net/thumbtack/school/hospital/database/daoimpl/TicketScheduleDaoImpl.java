package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.TicketScheduleDao;
import net.thumbtack.school.hospital.database.mappers.TicketScheduleMapper;
import net.thumbtack.school.hospital.database.model.TicketSchedule;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketScheduleDaoImpl implements TicketScheduleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketScheduleDaoImpl.class);

    private TicketScheduleMapper ticketScheduleMapper;

    public TicketScheduleDaoImpl() {
    }

    @Autowired
    public TicketScheduleDaoImpl(TicketScheduleMapper ticketScheduleMapper) {
        this.ticketScheduleMapper = ticketScheduleMapper;
    }

    @Override
    public List<TicketSchedule> getDayScheduleById(int scheduleId) throws ServerException {
        LOGGER.debug("DAO get Day Schedule by id {}", scheduleId);
        try {
            return ticketScheduleMapper.getDayScheduleById(scheduleId);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Day Schedule by id {} {}", scheduleId, ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }

}
