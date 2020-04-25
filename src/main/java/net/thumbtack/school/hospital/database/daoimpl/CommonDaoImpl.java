package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.CommonDao;
import net.thumbtack.school.hospital.database.mappers.AdminMapper;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommonDaoImpl implements CommonDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    private AdminMapper adminMapper;
    private UserMapper userMapper;

    public CommonDaoImpl() {
    }

    @Autowired
    public CommonDaoImpl (AdminMapper adminMapper, UserMapper userMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clear() throws ServerException {
        LOGGER.debug("Clear Database");
        try {
            userMapper.deleteAll();
            adminMapper.deleteAll();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't clear database");
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }
}
