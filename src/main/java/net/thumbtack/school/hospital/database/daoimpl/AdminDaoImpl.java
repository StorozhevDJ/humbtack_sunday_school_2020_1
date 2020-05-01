package net.thumbtack.school.hospital.database.daoimpl;

import net.thumbtack.school.hospital.database.dao.AdminDao;
import net.thumbtack.school.hospital.database.mappers.AdminMapper;
import net.thumbtack.school.hospital.database.mappers.UserMapper;
import net.thumbtack.school.hospital.database.model.Admin;
import net.thumbtack.school.hospital.database.model.Session;
import net.thumbtack.school.hospital.serverexception.ServerError;
import net.thumbtack.school.hospital.serverexception.ServerException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Component
public class AdminDaoImpl extends DaoImplBase implements AdminDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    private AdminMapper adminMapper;
    private UserMapper userMapper;

    public AdminDaoImpl() {
    }

    @Autowired
    public AdminDaoImpl (AdminMapper adminMapper, UserMapper userMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(rollbackFor = ServerException.class)
    public Admin insert(Admin admin) throws ServerException {
        LOGGER.debug("DAO insert Admin {}", admin);
            try {
            	// REVU а если такой логин уже существует ?
                userMapper.insert(admin.getUser());
                adminMapper.insert(admin);
            } catch (DataAccessException ex) {
                LOGGER.info("Can't insert Admin {} {}", admin, ex);
                throw new ServerException(ServerError.LOGIN_ALREADY_EXISTS);
            }
        return admin;
    }



    @Override
    public Admin getByToken(Session token) throws ServerException {
        LOGGER.debug("DAO get Admin by token {}", token);
        try {
            return adminMapper.getByToken(token);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Admin by token {} {}", token, ex);
            throw new ServerException(ServerError.TOKEN_INVALID);
        }
    }

    @Override
    public Admin getByUserId(int id) throws ServerException {
        LOGGER.debug("DAO get Admin by db ID {}", id);
        try {
            return adminMapper.getByUserId(id);
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get Admin by db ID {} {}", id, ex);
            throw new ServerException(ServerError.USER_ID_INVALID);
        }
    }

    @Override
    public int getCount() throws ServerException {
        LOGGER.debug("DAO get admins count");
        try {
            return adminMapper.getCount();
        } catch (DataAccessException ex) {
            LOGGER.info("Can't get admins count {} ", ex);
            throw new ServerException(ServerError.OTHER_ERROR);
        }
    }


}
